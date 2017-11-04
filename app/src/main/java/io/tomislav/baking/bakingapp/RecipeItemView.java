package io.tomislav.baking.bakingapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Recipe;

@EViewGroup(R.layout.recipe_item)
public class RecipeItemView extends CardView {
    @ViewById(R.id.recipe_name)
    TextView recipeName;

    @ViewById(R.id.servings_count)
    TextView servingsCount;

    @ViewById(R.id.steps_count)
    TextView stepsCount;

    public RecipeItemView(Context context) {
        super(context);
    }

    public void bind(Recipe recipe) {
        recipeName.setText(recipe.getName());
        servingsCount.setText(recipe.getServings() + "");
        stepsCount.setText(recipe.getSteps().size() + "");
    }
}
