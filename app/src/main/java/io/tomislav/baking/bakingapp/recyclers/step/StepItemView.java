package io.tomislav.baking.bakingapp.recyclers.step;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.models.Step;

@EViewGroup(R.layout.step_item)
public class StepItemView extends ConstraintLayout {
    @ViewById(R.id.description)
    TextView description;

    @ViewById(R.id.step_container)
    ConstraintLayout stepContainer;

    public StepItemView(Context context) {
        super(context);
    }

    public void bind(Step step) {
        description.setText(step.getOrder() + " - " + step.getShortDescription());
    }

    public void setOnClickListener(View.OnClickListener l) {
        stepContainer.setOnClickListener(l);
    }
}
