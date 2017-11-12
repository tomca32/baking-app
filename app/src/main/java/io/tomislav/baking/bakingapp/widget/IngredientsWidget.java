package io.tomislav.baking.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;

import io.tomislav.baking.bakingapp.DaoService;
import io.tomislav.baking.bakingapp.R;
import io.tomislav.baking.bakingapp.models.Recipe;

/**
 * Implementation of App Widget functionality.
 */
@EReceiver
public class IngredientsWidget extends AppWidgetProvider {

    @Bean
    DaoService daoService;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String title) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        setWidgetTitle(title, views);

        Intent intent = WidgetClickService_.intent(context).widgetClick().get();

        views.setOnClickPendingIntent(R.id.widget_container, PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Recipe recipe = daoService.getActiveRecipe();
        String title = recipe == null ? context.getString(R.string.widget_default) : recipe.getName();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, title);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void setWidgetTitle(String title, RemoteViews views) {
        views.setTextViewText(R.id.activeRecipeTitle, title);
    }
}

