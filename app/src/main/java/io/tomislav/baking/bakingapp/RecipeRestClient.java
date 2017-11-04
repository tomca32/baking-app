package io.tomislav.baking.bakingapp;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;

@Rest(converters = { MappingJackson2HttpMessageConverter.class })
public interface RecipeRestClient {
    @Get("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
    List<Recipe> getRecipes();
}
