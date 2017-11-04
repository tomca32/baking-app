package io.tomislav.baking.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @RestService
    RecipeRestClient recipeClient;

    @AfterViews
    void afterViews() {
        getRecipes();
    }

    @Background
    void getRecipes() {
        List<Recipe> recipes = recipeClient.getRecipes();
        Log.d("RECIPES: ", recipes.toString());
    }
}
