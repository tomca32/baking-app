package io.tomislav.baking.bakingapp;

import android.os.Parcelable;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.models.Step;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerHelper;
import io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase;
import io.tomislav.baking.bakingapp.recyclers.step.StepAdapter;

import static io.tomislav.baking.bakingapp.recyclers.base.RecyclerViewAdapterBase.getDivider;

@EFragment(R.layout.fragment_step_list)
public class StepListFragment extends Fragment implements RecyclerViewAdapterBase.AdapterClickCallback<Step> {
    interface StepClickCallback {
        void stepClickCallback(Step step, int position);
    }

    @ViewById(R.id.step_list)
    RecyclerView stepList;

    @ViewById(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bean
    StepAdapter stepAdapter;

    @RestService
    RecipeRestClient recipeClient;

    @Bean
    DaoService daoService;

    @InstanceState
    List<Step> steps;

    @InstanceState
    long recipeId;

    @InstanceState
    Parcelable listState;

    StepClickCallback activityCallback;

    @Override
    public void onPause() {
        super.onPause();
        listState = stepList.getLayoutManager().onSaveInstanceState();
    }

    @AfterViews
    void configureFragment() {
        stepAdapter.setClickCallback(this);
        stepList.addItemDecoration(getDivider(stepList));
        stepList.setAdapter(stepAdapter);
        updateSteps();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {refreshSteps();}
        });
        RecyclerHelper.restoreRecyclerViewState(listState, stepList);
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public void adapterClickCallback(Step item, int position) {
        activityCallback.stepClickCallback(item, position);
    }

    @Background
    public void refreshSteps() {
        List<Recipe> recipes = recipeClient.getRecipes();
        daoService.overwriteRecipes(recipes);
        updateSteps();

    }

    @MainThread
    private void updateSteps() {
        this.steps = daoService.getRecipeDao().load(recipeId).getSteps();
        stepAdapter.replaceItems(steps);
        updateView();
    }

    @UiThread
    void updateView() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
