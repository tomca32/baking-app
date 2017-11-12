package io.tomislav.baking.bakingapp;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import io.tomislav.baking.bakingapp.models.ActiveRecipe;
import io.tomislav.baking.bakingapp.models.ActiveRecipeDao;
import io.tomislav.baking.bakingapp.models.DaoSession;
import io.tomislav.baking.bakingapp.models.Ingredient;
import io.tomislav.baking.bakingapp.models.IngredientDao;
import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.RecipeDao;
import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.models.StepDao;

@EBean
public class DaoService {
    @RootContext Context context;

    @org.androidannotations.annotations.App
    App app;


    public DaoSession getDaoSession() {
        return app.getDaoSession();
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

    public ActiveRecipeDao getActiveRecipeDao() {
        return getDaoSession().getActiveRecipeDao();
    }

    public void overwriteRecipes(final List<Recipe> recipes) {
        getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<Ingredient> ingredients = new ArrayList<>();
                List<Step> steps = new ArrayList<>();
                for (Recipe recipe : recipes) {
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        ingredient.setRecipeId(recipe.getId());
                        ingredients.add(ingredient);
                    }

                    for (Step step : recipe.getSteps()) {
                        step.setRecipeId(recipe.getId());
                        steps.add(step);
                    }
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

    public void updateActiveRecipe(final long recipeId) {
        getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                getActiveRecipeDao().deleteAll();
                ActiveRecipe activeRecipe = new ActiveRecipe();
                activeRecipe.setActiveRecipeId(recipeId);
                getActiveRecipeDao().insert(activeRecipe);
            }
        });
    }

    public void updateActiveRecipe(Recipe recipe) {
        updateActiveRecipe(recipe.getId());
    }

    public Recipe getActiveRecipe() {
        ActiveRecipeDao dao = getActiveRecipeDao();
        List<ActiveRecipe> activeRecipes = dao.loadAll();
        if (activeRecipes.size() == 0) {
            return null;
        }
        return getRecipeDao().load(activeRecipes.get(0).getActiveRecipeId());

    }
}
