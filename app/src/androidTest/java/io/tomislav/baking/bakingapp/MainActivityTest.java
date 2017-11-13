package io.tomislav.baking.bakingapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity_> activityActivityTestRule = new ActivityTestRule<>(MainActivity_.class, false, false);

    private IdlingResource idlingResource;

    @Before
    public void before() {
        Intent intent = MainActivity_.intent(InstrumentationRegistry.getTargetContext()).useIdleResource(true).get();
        MainActivity_ activity = activityActivityTestRule.launchActivity(intent);
        idlingResource = activity.getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void checkThatViewGetPopulatedWithRecipes() {
        onView(withId(R.id.recipe_list)).check(matches(hasDescendant(withId(R.id.recipe_name))));
        onView(withId(R.id.recipe_list)).check(matches(hasDescendant(withId(R.id.recipe_image))));
    }

    @Test
    public void clickingOnRecipeCardShowsRecipeSteps() {
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Nutella Pie")), click()));

        onView(allOf(isAssignableFrom(TextView.class), withParent(isAssignableFrom(Toolbar.class)))).check(matches(withText("Nutella Pie")));
        onView(withId(R.id.step_list)).check(matches(hasDescendant(withId(R.id.description))));
    }

    @After
    public void after() {
        Espresso.unregisterIdlingResources(idlingResource);
    }
}
