package io.tomislav.baking.bakingapp;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.tomislav.baking.bakingapp.models.DaoSession;
import io.tomislav.baking.bakingapp.models.Ingredient;
import io.tomislav.baking.bakingapp.models.IngredientDao;
import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.RecipeDao;
import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.models.StepDao;

abstract public class ApplicationActivity extends AppCompatActivity {
    public DaoSession getDaoSession() {
        return ((App) getApplication()).getDaoSession();
    }

    public RecipeDao getRecipeDao() {
        return getDaoSession().getRecipeDao();
    }

    public StepDao getStepDao() {
        return getDaoSession().getStepDao();
    }

    public IngredientDao getIngredientDao() {
        return getDaoSession().getIngredientDao();
    }

    public void overwriteRecipes(final List<Recipe> recipes) {
        getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<Ingredient> ingredients = new ArrayList<>();
                List<Step> steps = new ArrayList<>();
                for (Recipe recipe : recipes) {
                    ingredients.addAll(recipe.getIngredients());
                    steps.addAll(recipe.getSteps());
                }
                overwriteSteps(steps);
                overwriteIngredients(ingredients);
                getRecipeDao().deleteAll();
                getRecipeDao().insertInTx(recipes);
            }
        });
    }

    public void overwriteSteps(List<Step> newSteps) {
        getStepDao().deleteAll();
        getStepDao().insertInTx(newSteps);
    }

    public void overwriteIngredients(List<Ingredient> ingredients) {
        getIngredientDao().deleteAll();
        getIngredientDao().insertInTx(ingredients);
    }

}
