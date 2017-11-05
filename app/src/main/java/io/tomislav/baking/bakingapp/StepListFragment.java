package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.recyclers.step.StepAdapter;

@EFragment(R.layout.fragment_step_list)
public class StepListFragment extends Fragment {

    @ViewById(R.id.step_list)
    RecyclerView stepList;

    @Bean
    StepAdapter stepAdapter;

    List<Step> steps;

    @AfterViews
    void configureFragment() {
        stepList.addItemDecoration(getDivider(stepList));
        stepAdapter.replaceItems(steps);
        stepList.setAdapter(stepAdapter);
    }

    public void setSteps(List<Step> newSteps) {
        steps = newSteps;
    }

    private DividerItemDecoration getDivider(RecyclerView recycler) {
        return new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
    }
}
