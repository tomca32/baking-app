package io.tomislav.baking.bakingapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

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

}
