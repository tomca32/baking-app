package io.tomislav.baking.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.widget.IngredientsWidget_;

@EActivity(R.layout.activity_step_list_detail)
public class StepListDetailActivity extends DrawerActivity implements StepListFragment_.StepClickCallback {

    @ViewById(R.id.detail_fragment_container)
    FrameLayout detailFragmentContainer;

    @Extra("recipe")
    public Recipe recipe;

    @Bean
    DaoService daoService;

    @AfterExtras
    public void afterExtras() {
        setTitle(recipe.getName());
        ingredients = recipe.getIngredients();
    }

    @InstanceState
    int selectedStep = 0;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof StepListFragment_) {
            StepListFragment_ stepFragment = (StepListFragment_) fragment;
            stepFragment.setRecipeId(recipe.getId());
            stepFragment.activityCallback = this;
        }
    }

    @AfterViews
    public void afterViews() {
        super.afterViews();
        toolbar.setTitle(recipe.getName());
        updateTabletDetailView();
        setActiveRecipe();
        updateWidgets();
    }

    @Override
    public void stepClickCallback(Step step, int position) {
        selectedStep = position;
        updateDetailView();
    }

    private void updateDetailView() {
        if (detailFragmentContainer == null) {
            StepDetailActivity_.intent(this).ingredients(ingredients).step(recipe.getSteps().get(selectedStep)).recipeName(recipe.getName()).start();
            return;
        }
        updateTabletDetailView();
    }

    private void updateTabletDetailView() {
        if (detailFragmentContainer == null) {
            return;
        }
        StepDetailFragment fragment = StepDetailFragment_.builder()
                .step(recipe.getSteps().get(selectedStep))
                .build();
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, fragment).commit();
    }

    @Background
    void setActiveRecipe() {
        daoService.updateActiveRecipe(recipe);
    }

    @Background
    void updateWidgets() {
        Intent intent = new Intent(this, IngredientsWidget_.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetI‌​ds(new ComponentName(getApplication(), IngredientsWidget_.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }
}
