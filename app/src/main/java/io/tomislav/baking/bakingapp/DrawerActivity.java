package io.tomislav.baking.bakingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Ingredient;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerHelper;
import io.tomislav.baking.bakingapp.recyclers.ingredient.IngredientAdapter;

import static io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase.getDivider;

@EActivity
public abstract class DrawerActivity extends AppCompatActivity {
    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.ingredients_list)
    RecyclerView ingredientsList;

    @ViewById(R.id.all_recipes_button)
    LinearLayout allRecipesButton;

    protected MaterialMenuDrawable materialMenu;

    @InstanceState
    protected boolean isDrawerOpened;

    @InstanceState
    @Extra("ingredients")
    protected List<Ingredient> ingredients;

    @InstanceState
    Parcelable listState;

    @Bean
    IngredientAdapter ingredientAdapter;

    @Override
    public void onPause() {
        super.onPause();
        if (ingredientsList != null) {
            listState = ingredientsList.getLayoutManager().onSaveInstanceState();
        }
    }

    @Click
    void allRecipesButton() {
        Intent homeIntent = new Intent(this, MainActivity_.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void afterViews() {
        setSupportActionBar(toolbar);

        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        setupDrawer();
        setNavigationIconInteractions();
    }

    protected void setNavigationIconInteractions() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDrawerOpened) {
                    drawerLayout.closeDrawer(Gravity.START, true);
                } else {
                    drawerLayout.openDrawer(Gravity.START, true);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected void setupDrawer() {
        ingredientsList.setAdapter(ingredientAdapter);
        ingredientsList.addItemDecoration(getDivider(ingredientsList));
        ingredientAdapter.replaceItems(ingredients);
        RecyclerHelper.restoreRecyclerViewState(listState, ingredientsList);


        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if(newState == DrawerLayout.STATE_IDLE) {
                    if(isDrawerOpened) {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        materialMenu.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }
        });
    }
}
