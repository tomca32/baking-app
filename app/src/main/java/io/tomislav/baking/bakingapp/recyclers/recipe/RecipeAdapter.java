package io.tomislav.baking.bakingapp.recyclers.recipe;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase;
import io.tomislav.baking.bakingapp.recyclers.base.ViewWrapper;

@EBean
public class RecipeAdapter extends RecyclerViewAdapterBase<Recipe, RecipeItemView> {
    @RootContext
    public Context context;

    @Override
    protected RecipeItemView onCreateItemView(ViewGroup parent, int viewType) {
        return RecipeItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RecipeItemView> holder, int position) {
        RecipeItemView view = holder.getView();
        Recipe recipe = items.get(position);

        view.bind(recipe, context);
    }
}
