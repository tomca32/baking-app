package io.tomislav.baking.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;

import io.tomislav.baking.bakingapp.DaoService;

@EService
public class IngredientsRemoteViewsService extends RemoteViewsService {

    @Bean
    DaoService daoService;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), daoService);
    }
}
