package io.tomislav.baking.bakingapp;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Step;

@EFragment(R.layout.fragment_step_detail)
public class StepDetailFragment extends Fragment{

    @ViewById(R.id.description)
    TextView description;

    @FragmentArg("step")
    Step step;


    @AfterViews
    public void afterViews() {
        description.setText(step.getDescription());
    }
}
