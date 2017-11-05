package io.tomislav.baking.bakingapp.recyclers.ingredient;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import io.tomislav.baking.bakingapp.models.Ingredient;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase;
import io.tomislav.baking.bakingapp.recyclers.base.ViewWrapper;

@EBean
public class IngredientAdapter extends RecyclerViewAdapterBase<Ingredient, IngredientItemView> {
    @RootContext
    public Context context;

    @Override
    protected IngredientItemView onCreateItemView(ViewGroup parent, int viewType) {
        return IngredientItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<IngredientItemView> holder, int position) {
        IngredientItemView view = holder.getView();
        Ingredient ingredient = items.get(position);

        view.bind(ingredient);
    }
}
