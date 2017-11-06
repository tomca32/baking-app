package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
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

    @ViewById(R.id.landscape_step_detail)
    FrameLayout landscapeFrame;

    @AfterViews
    @Override
    public void afterViews() {
        if (landscapeFrame == null) {
            super.afterViews();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        StepDetailFragment_ stepDetailFragment = (StepDetailFragment_) fragment;
        stepDetailFragment.step = step;
        stepDetailFragment.landscapePhone = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
    }
}
