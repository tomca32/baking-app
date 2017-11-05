package io.tomislav.baking.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static io.tomislav.baking.bakingapp.helpers.atPosition;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity_> activityActivityTestRule = new ActivityTestRule<>(MainActivity_.class);

    private IdlingResource idlingResource;

    @Before
    public void before() {
        MainActivity_ activity = activityActivityTestRule.getActivity();
        idlingResource = activity.getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
        ((SimpleIdlingResource) idlingResource).setIdleState(false);
    }

    @Test
    public void checkThatViewGetPopulatedWithRecipes() {
        onView(withId(R.id.recipe_list)).check(matches(atPosition(0, hasDescendant(withId(R.id.recipe_name)))));
        onView(withId(R.id.recipe_list)).check(matches(atPosition(0, hasDescendant(withId(R.id.recipe_image)))));
    }

    @After
    public void after() {
        Espresso.unregisterIdlingResources(idlingResource);
    }
}
