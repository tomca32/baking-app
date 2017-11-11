package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase;
import io.tomislav.baking.bakingapp.recyclers.step.StepAdapter;

import static io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase.getDivider;

@EFragment(R.layout.fragment_step_list)
public class StepListFragment extends Fragment implements RecyclerViewAdapterBase.AdapterClickCallback<Step>{
    interface StepClickCallback {
        void stepClickCallback(Step step, int position);
    }

    @ViewById(R.id.step_list)
    RecyclerView stepList;

    @Bean
    StepAdapter stepAdapter;

    List<Step> steps;
    StepClickCallback activityCallback;

    @AfterViews
    void configureFragment() {
        stepAdapter.setClickCallback(this);
        stepList.addItemDecoration(getDivider(stepList));
        stepAdapter.replaceItems(steps);
        stepList.setAdapter(stepAdapter);
    }

    public void setSteps(List<Step> newSteps) {
        steps = newSteps;
    }

    @Override
    public void adapterClickCallback(Step item, int position) {
        activityCallback.stepClickCallback(item, position);
    }
}