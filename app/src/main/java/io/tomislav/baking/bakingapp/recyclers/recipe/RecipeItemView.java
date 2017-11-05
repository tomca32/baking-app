package io.tomislav.baking.bakingapp.recyclers.recipe;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.StepListDetailActivity_;
import io.tomislav.baking.bakingapp.models.Recipe;

@EViewGroup(R.layout.recipe_item)
public class RecipeItemView extends CardView {
    @ViewById(R.id.recipe_name)
    public TextView recipeName;

    @ViewById(R.id.servings_count)
    public TextView servingsCount;

    @ViewById(R.id.steps_count)
    public TextView stepsCount;

    public RecipeItemView(Context context) {
        super(context);
    }

    private Recipe recipe;

    public void bind(Recipe recipe) {
        this.recipe = recipe;
        recipeName.setText(recipe.getName());
        servingsCount.setText(recipe.getServings() + "");
        stepsCount.setText(recipe.getSteps().size() + "");
    }

    @Click(R.id.item_container)
    void handleRecipeClick() {
        StepListDetailActivity_.intent(getContext()).recipe(recipe).start();
    }

}
