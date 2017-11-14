package io.tomislav.baking.bakingapp.recyclers.base;

import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

public class RecyclerHelper {

    public static void restoreRecyclerViewState(final Parcelable listState, final RecyclerView recyclerView) {
        if (listState != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.getLayoutManager().onRestoreInstanceState(listState);
                }
            }, 150);
        }
    }
}
