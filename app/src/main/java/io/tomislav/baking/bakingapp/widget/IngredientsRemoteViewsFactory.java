package io.tomislav.baking.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import io.tomislav.baking.bakingapp.App;
import io.tomislav.baking.bakingapp.DaoService;
import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.models.Ingredient;
import io.tomislav.baking.bakingapp.models.Recipe;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final DaoService daoService;
    App app;
    Context context;

    List<Ingredient> ingredients;

    public IngredientsRemoteViewsFactory(Context context, DaoService daoService) {
        this.context = context;
        app = (App) context.getApplicationContext();
        this.daoService = daoService;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Recipe activeRecipe = daoService.getActiveRecipe();
        if (activeRecipe == null) {
            return;
        }
        ingredients = activeRecipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Ingredient ingredient = ingredients.get(i);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_item);
        views.setTextViewText(R.id.widget_ingredient_name, ingredient.getIngredient());
        views.setTextViewText(R.id.widget_ingredient_measure, ingredient.getMeasure());
        views.setTextViewText(R.id.widget_ingredient_quantity, ingredient.getQuantity() + "");

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
