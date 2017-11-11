package io.tomislav.baking.bakingapp;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.tomislav.baking.bakingapp.models.DaoSession;
import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.RecipeDao;
import io.tomislav.baking.bakingapp.recyclers.recipe.RecipeAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Nullable private SimpleIdlingResource idlingResource;

    @RestService
    RecipeRestClient recipeClient;

    @ViewById(R.id.recipe_list)
    RecyclerView recyclerView;

    @ViewById(R.id.progress_bar)
    View progressBar;

    @Bean
    RecipeAdapter recipeAdapter;

    @InstanceState
    List<Recipe> recipes;

    @AfterViews
    void configureActivity() {
        recyclerView.setAdapter(recipeAdapter);
        if (recipes == null) {
            getRecipes();
        } else {
            recipeAdapter.replaceItems(recipes);
        }

    }

    @Background
    void getRecipes() {
        showProgress();
        recipes = recipeClient.getRecipes();
        updateRecipes(recipes);
        setIdlingResourcePassive();
        recipeAdapter.replaceItems(recipes);
        hideProgress();
    }

    @UiThread
    void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @UiThread
    void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setIdlingResourcePassive() {
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
    }

    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    private void updateRecipes(List<Recipe> recipes) {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        RecipeDao recipeDao = daoSession.getRecipeDao();

        recipeDao.insertInTx(recipes);
    }
}
