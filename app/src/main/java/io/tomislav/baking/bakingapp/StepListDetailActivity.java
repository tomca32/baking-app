package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import io.tomislav.baking.bakingapp.models.Recipe;

@EActivity(R.layout.activity_step_list_detail)
public class StepListDetailActivity extends AppCompatActivity {

    @Extra("recipe")
    public Recipe recipe;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        ((StepListFragment_)fragment).setSteps(recipe.getSteps());
    }
}
