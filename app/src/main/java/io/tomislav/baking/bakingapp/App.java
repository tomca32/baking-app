package io.tomislav.baking.bakingapp;

import android.app.Application;

import org.androidannotations.annotations.EApplication;
import org.greenrobot.greendao.database.Database;

import io.tomislav.baking.bakingapp.models.DaoMaster;
import io.tomislav.baking.bakingapp.models.DaoSession;

@EApplication
public class App extends Application {
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "recipe-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
