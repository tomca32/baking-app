package io.tomislav.baking.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;

import static android.support.test.internal.util.Checks.checkNotNull;

public class helpers {

    public static List<Recipe> getMockRecipes(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        List<Recipe> recipes = new ArrayList<>();
        try {
            recipes = mapper.readValue(loadJsonMock(context), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static String loadJsonMock(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("mock_recipes_response.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
