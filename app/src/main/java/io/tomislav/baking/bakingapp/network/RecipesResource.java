package io.tomislav.baking.bakingapp.network;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.tomislav.baking.bakingapp.DaoService;
import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.util.ConnectionHelper;

@EBean
public class RecipesResource {
    private Context context;

    @RestService
    RecipeRestClient recipeClient;

    @Bean
    ConnectionHelper connectionHelper;

    @Bean
    DaoService daoService;

    public RecipesResource(Context context) {
        this.context = context;
    }

    public List<Recipe> getAndStoreRecipes() {
        if (connectionHelper.isConnected()) {
            List<Recipe> recipes = recipeClient.getRecipes();
            daoService.overwriteRecipes(recipes);
            return recipes;
        } else {
            showConnectionErrorToast();
            return null;
        }
    }

    @UiThread
    void showConnectionErrorToast() {
        Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show();
    }
}
