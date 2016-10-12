package hm.orz.chaos114.android.circlewatcher.modules.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import hm.orz.chaos114.android.circlewatcher.App;
import hm.orz.chaos114.android.circlewatcher.R;
import hm.orz.chaos114.android.circlewatcher.databinding.FragmentBuildListBinding;
import hm.orz.chaos114.android.circlewatcher.entity.Build;
import hm.orz.chaos114.android.circlewatcher.entity.BuildList;
import hm.orz.chaos114.android.circlewatcher.network.CircleCiService;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Show builds list.
 */
public class BuildFragment extends Fragment {

    @Inject
    CircleCiService circleCiService;
    @Inject
    Realm realm;

    private OnListFragmentInteractionListener listener;
    private FragmentBuildListBinding binding;
    private BuildRecyclerViewAdapter adapter;

    public static BuildFragment newInstance() {
        return new BuildFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplicationContext()).getApplicationComponent().activityComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchBuildsWithCache();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_build_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.list.setHasFixedSize(true);
        binding.list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BuildRecyclerViewAdapter(listener);
        binding.list.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(this::fetchBuilds);

        showRefreshing(true);
        fetchBuildsWithCache();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchBuilds() {
        circleCiService.getRecentBuilds()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(list);
                    realm.commitTransaction();
                    adapter.resetItems(list);
                }, throwable -> {
                    Timber.d(throwable, "error");
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }, () -> {
                    showRefreshing(false);
                });
    }

    private void fetchBuildsWithCache() {
        RealmResults<Build> builds = realm.where(Build.class)
                .findAll();
        if (builds.size() != 0) {
            Timber.d("Build has cache");
            showRefreshing(false);
            adapter.resetItems(new BuildList(realm.copyFromRealm(builds)));
            return;
        }
        fetchBuilds();
    }

    private void showRefreshing(boolean show) {
        if (show) {
            binding.swipeRefresh.setRefreshing(true);
        } else {
            if (binding.swipeRefresh.isRefreshing()) {
                binding.swipeRefresh.setRefreshing(false);
            }
        }
    }

    public interface OnListFragmentInteractionListener {
        void onSelectBuild(Build item);
    }
}
