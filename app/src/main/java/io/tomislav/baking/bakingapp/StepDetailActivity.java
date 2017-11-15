package io.tomislav.baking.bakingapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Step;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

@EActivity(R.layout.activity_step_detail)
public class StepDetailActivity extends DrawerActivity {
    @InstanceState
    @Extra("step")
    Step step;

    @InstanceState
    @Extra("recipeName")
    String recipeName;

    @ViewById(R.id.landscape_step_detail)
    FrameLayout landscapeFrame;

    @ViewById(R.id.recipe_button)
    LinearLayout recipeButton;

    @AfterViews
    @Override
    public void afterViews() {
        if (landscapeFrame == null) {
            super.afterViews();
            toolbar.setTitle(recipeName);
            recipeButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        StepDetailFragment_ stepDetailFragment = (StepDetailFragment_) fragment;
        stepDetailFragment.step = step;
        stepDetailFragment.landscapePhone = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
    }

    @Click
    void recipeButton() {
        Intent stepList = new Intent(this, StepListDetailActivity_.class);
        stepList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(stepList);
    }
}
