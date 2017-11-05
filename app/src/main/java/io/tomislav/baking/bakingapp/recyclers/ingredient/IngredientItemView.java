package io.tomislav.baking.bakingapp.recyclers.ingredient;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.models.Ingredient;

@EViewGroup(R.layout.ingredient_item)
public class IngredientItemView extends LinearLayout {
    @ViewById(R.id.ingredient_name)
    public TextView ingredientName;

    @ViewById(R.id.ingredient_quantity)
    public TextView ingredientQuantity;

    @ViewById(R.id.ingredient_measure)
    public TextView ingredientMeasure;

    public IngredientItemView(Context context) {
        super(context);
    }

    private Ingredient ingredient;

    public void bind(Ingredient ingredient) {
        this.ingredient = ingredient;
        ingredientName.setText(StringUtils.capitalize(ingredient.getIngredient()));
        ingredientQuantity.setText(ingredient.getQuantity() + "");
        ingredientMeasure.setText(ingredient.getMeasure().toUpperCase());
    }
}
