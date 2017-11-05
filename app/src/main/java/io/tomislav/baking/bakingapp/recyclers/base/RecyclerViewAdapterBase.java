package io.tomislav.baking.bakingapp.recyclers.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

@EBean
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {
    public interface AdapterClickCallback<U> {
        void adapterClickCallback(U item, int index);
    }

    protected List<T> items = new ArrayList<>();

    protected AdapterClickCallback callback;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setClickCallback(AdapterClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @UiThread
    public void replaceItems(List<T> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    public static DividerItemDecoration getDivider(RecyclerView recycler) {
        return new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
    }
}
