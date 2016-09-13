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

import hm.orz.chaos114.android.circlewatcher.R;
import hm.orz.chaos114.android.circlewatcher.databinding.FragmentBuildListBinding;
import hm.orz.chaos114.android.circlewatcher.entity.Build;
import hm.orz.chaos114.android.circlewatcher.network.ApiClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Show builds list.
 */
public class BuildFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener listener;
    private FragmentBuildListBinding binding;
    private BuildRecyclerViewAdapter adapter;

    public static BuildFragment newInstance(int columnCount) {
        BuildFragment fragment = new BuildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_build_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BuildRecyclerViewAdapter(listener);
        binding.list.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(this::fetchBuilds);

        showRefreshing(true);
        fetchBuilds();
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

    private void fetchBuilds() {
        ApiClient.getClient(getContext()).getRecentBuilds()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    showRefreshing(false);
                    adapter.resetItems(list);
                }, throwable -> {
                    showRefreshing(false);
                    Timber.d(throwable, "error");
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
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
