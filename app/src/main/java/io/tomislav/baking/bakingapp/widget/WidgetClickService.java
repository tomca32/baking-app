package io.tomislav.baking.bakingapp.widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import io.tomislav.baking.bakingapp.DaoService;
import io.tomislav.baking.bakingapp.MainActivity_;
import io.tomislav.baking.bakingapp.StepListDetailActivity_;
import io.tomislav.baking.bakingapp.models.Recipe;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@EIntentService
public class WidgetClickService extends IntentService {

    @Bean
    DaoService daoService;

    public WidgetClickService() {
        super("WidgetClickService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {}


    @ServiceAction
    void widgetClick() {
        Recipe activeRecipe = daoService.getActiveRecipe();
        if (activeRecipe == null) {
            launchMainActivity();
            return;
        }
        launchRecipeActivity(activeRecipe);
    }

    private void launchMainActivity() {
        MainActivity_.intent(this).flags(FLAG_ACTIVITY_NEW_TASK).start();
    }

    private void launchRecipeActivity(Recipe recipe) {
        StepListDetailActivity_.intent(this).recipe(recipe).flags(FLAG_ACTIVITY_NEW_TASK).start();
    }
}
