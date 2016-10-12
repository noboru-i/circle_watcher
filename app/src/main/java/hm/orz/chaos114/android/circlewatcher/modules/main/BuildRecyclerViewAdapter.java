package hm.orz.chaos114.android.circlewatcher.modules.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import hm.orz.chaos114.android.circlewatcher.R;
import hm.orz.chaos114.android.circlewatcher.databinding.FragmentBuildBinding;
import hm.orz.chaos114.android.circlewatcher.entity.Build;
import hm.orz.chaos114.android.circlewatcher.entity.BuildList;
import timber.log.Timber;

/**
 * Adapter for BuildFragment.
 */
class BuildRecyclerViewAdapter extends RecyclerView.Adapter<BuildRecyclerViewAdapter.ViewHolder> {

    private final BuildList values;
    private final BuildFragment.OnListFragmentInteractionListener listener;

    BuildRecyclerViewAdapter(BuildFragment.OnListFragmentInteractionListener listener) {
        this.listener = listener;

        values = new BuildList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_build, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Build item = values.get(position);
        holder.binding.setBuild(new BuildViewModel(holder.itemView.getContext(), item));

        holder.binding.cardView.setOnClickListener(v -> {
            Timber.d("test click %s", v);
            if (listener != null) {
                listener.onSelectBuild(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    void resetItems(BuildList newList) {
        if (newList == null) {
            return;
        }
        values.clear();
        values.addAll(newList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentBuildBinding binding;

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}
