package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;

import io.tomislav.baking.bakingapp.models.Step;

@EActivity(R.layout.activity_step_detail)
public class StepDetailActivity extends DrawerActivity {
    @InstanceState
    @Extra("step")
    Step step;

    @AfterViews
    @Override
    public void afterViews() {
        super.afterViews();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        ((StepDetailFragment_) fragment).step = step;
    }
}
