package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import io.tomislav.baking.bakingapp.models.Recipe;

@EActivity(R.layout.activity_step_list_detail)
public class StepListDetailActivity extends AppCompatActivity {

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
        try {
            ((StepListFragment_)fragment).setSteps(recipe.getSteps());
            return;
        } catch (ClassCastException e) {
            Log.d("CAST ERROR: ", e.getStackTrace().toString());
        }
        ((StepDetailFragment_)fragment).setStep(recipe.getSteps().get(selectedStep));
    }
}
