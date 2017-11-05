package io.tomislav.baking.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.sql.DriverManager;
import java.util.List;

import io.tomislav.baking.bakingapp.models.Recipe;
import io.tomislav.baking.bakingapp.rest.RecipeClient;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static io.tomislav.baking.bakingapp.helpers.atPosition;
import static io.tomislav.baking.bakingapp.helpers.getMockRecipes;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class, PowerMockRunner.class)
@PrepareForTest(DriverManager.class)
public class MainActivityTest {

    @Rule public ActivityTestRule<MainActivity_> rule = new ActivityTestRule<>(MainActivity_.class, false, false);
    private IdlingResource idlingResource;

    @Before
    public void before() {
        client = Mockito.mock(RecipeClient.class);
        List<Recipe> mockRecipes = getMockRecipes(getTargetContext());
        when(client.getRecipes()).thenReturn(mockRecipes);

        rule.launchActivity(new Intent());
        idlingResource = rule.getActivity().getIdlingResource();
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
