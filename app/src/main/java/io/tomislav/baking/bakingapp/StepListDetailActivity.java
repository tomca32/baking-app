package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.Step;

@EActivity(R.layout.activity_step_list_detail)
public class StepListDetailActivity extends AppCompatActivity implements StepListFragment_.StepClickCallback {

    @ViewById(R.id.detail_fragment_container)
    FrameLayout detailFragmentContainer;


    @Extra("recipe")
    public Recipe recipe;

    @AfterExtras
    public void afterExtras() {
        setTitle(recipe.getName());
    }

    private int selectedStep = 0;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof StepDetailFragment_) {
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
}
