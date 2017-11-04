package io.tomislav.baking.bakingapp;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;

@EBean
public class RecipeAdapter extends RecyclerViewAdapterBase<Recipe, RecipeItemView> {
    @RootContext
    Context context;

    @Override
    protected RecipeItemView onCreateItemView(ViewGroup parent, int viewType) {
        return RecipeItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RecipeItemView> holder, int position) {
        RecipeItemView view = holder.getView();
        Recipe recipe = items.get(position);

        view.bind(recipe);
    }

    @UiThread
    public void replaceItems(List<Recipe> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }
}
