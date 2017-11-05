package io.tomislav.baking.bakingapp.recyclers.step;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase;
import io.tomislav.baking.bakingapp.recyclers.base.ViewWrapper;

@EBean
public class StepAdapter extends RecyclerViewAdapterBase<Step, StepItemView> {
    @RootContext
    public Context context;

    @Override
    protected StepItemView onCreateItemView(ViewGroup parent, int viewType) {
        return StepItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<StepItemView> holder, int position) {
        StepItemView view = holder.getView();
        Step step = items.get(position);
        view.bind(step);
        setClickListener(view, step, position);
    }

    private void setClickListener(View view, final Step step, final int position) {
        if (callback != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.adapterClickCallback(step, position);
                }
            });
        }
    }
}
