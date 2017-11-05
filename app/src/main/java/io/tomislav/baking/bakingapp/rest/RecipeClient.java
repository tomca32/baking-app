package io.tomislav.baking.bakingapp.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.tomislav.baking.bakingapp.RecipeRestClient;
import io.tomislav.baking.bakingapp.models.Recipe;

@EBean(scope = EBean.Scope.Singleton)
public class RecipeClient {
    @RestService
    public static RecipeRestClient __client;

    public static List<Recipe> getRecipes() {
        return __client.getRecipes();
    }
}
