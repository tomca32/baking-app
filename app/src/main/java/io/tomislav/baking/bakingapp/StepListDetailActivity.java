package io.tomislav.baking.bakingapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.recyclers.ingredient.IngredientAdapter;

import static io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase.getDivider;

@EActivity(R.layout.activity_step_list_detail)
public class StepListDetailActivity extends AppCompatActivity implements StepListFragment_.StepClickCallback {

    @ViewById(R.id.detail_fragment_container)
    FrameLayout detailFragmentContainer;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.ingredients_list)
    RecyclerView ingredientsList;

    @Bean
    IngredientAdapter ingredientAdapter;

    @Extra("recipe")
    public Recipe recipe;

    private MaterialMenuDrawable materialMenu;
    private boolean isDrawerOpened;

    @AfterExtras
    public void afterExtras() {
        setTitle(recipe.getName());
    }

    @AfterViews
    public void afterViews() {
        setSupportActionBar(toolbar);
        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        setupDrawer();
        setNavigationIconInteractions();
    }

    private int selectedStep = 0;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof StepListFragment_) {
            StepListFragment_ stepFragment = (StepListFragment_) fragment;
            stepFragment.setSteps(recipe.getSteps());
            stepFragment.activityCallback = this;
            updateDetailView();
        }
    }

    @Override
    public void stepClickCallback(Step step, int position) {
        selectedStep = position;
        updateDetailView();
    }

    private void updateDetailView() {
        if (detailFragmentContainer == null) return;

        StepDetailFragment fragment = StepDetailFragment_.builder().step(recipe.getSteps().get(selectedStep)).build();
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, fragment).commit();
    }

    private void setNavigationIconInteractions() {
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

    private void setupDrawer() {
        ingredientsList.setAdapter(ingredientAdapter);
        ingredientsList.addItemDecoration(getDivider(ingredientsList));
        ingredientAdapter.replaceItems(recipe.getIngredients());


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
