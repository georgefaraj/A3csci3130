package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.acme.a3csci3130", appContext.getPackageName());
    }

    @Test
    public void createValidBusy() throws InterruptedException{
        /*name number address
        Spinner primaryB province
        onView(withId(R.id.editText)).perform(typeText("GoodPassword!1"),closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.button)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.responseText)).check(matches(withText("Strong!")));
        */

        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).perform(typeText("ABC Business"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.number)).perform(typeText("1234"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.address)).perform(typeText("1234 Fake St."), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.primaryB)).perform(click());
        Thread.sleep(500);
        onData(anything()).atPosition(1).perform(click());
        Thread.sleep(1500);

        onView(withId(R.id.province)).perform(click());
        Thread.sleep(500);
        onData(anything()).atPosition(1).perform(click());
        Thread.sleep(1500);

        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(2000);
        //onView(withId(R.id.listView)).check(matches(not(hasDescendant(withText("should not exist")))));

        //
        //onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        //

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).
                //onChildView(withId(R.id.text1)).
                check(matches(withText("ABC Business")));

        //onView(withId(R.id.listView)).check(matches((hasDescendant(withText("should not exist")))));

    }
}