package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.AllOf.allOf;
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

        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).perform(typeText("ABC Business"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.number)).perform(typeText("123456789"), ViewActions.closeSoftKeyboard());
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

        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                numberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(numberOfAdapterItems[0] - 1).check(matches(withText("ABC Business")));
    }

    @Test
    public void deleteBusy() throws InterruptedException{
        Thread.sleep(2000);
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                numberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(numberOfAdapterItems[0] - 1).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.deleteButton)).perform(click());
        Thread.sleep(2000);
        final int[] NEWnumberOfAdapterItems = new int[1];
        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                NEWnumberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        assertTrue(NEWnumberOfAdapterItems[0]==numberOfAdapterItems[0]-1);
    }

    @Test
    public void updateBusy() throws InterruptedException{
        Thread.sleep(2000);
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;
                numberOfAdapterItems[0] = listView.getAdapter().getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(numberOfAdapterItems[0] - 1).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).perform(clearText(),typeText("CBA Business"),closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.updateButton)).perform(click());
        Thread.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(numberOfAdapterItems[0] - 1).check(matches(withText("CBA Business")));
    }

    @Test
    public void createInvalidBusy() throws InterruptedException {

        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).perform(clearText(),typeText("X"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.number)).perform(clearText(),typeText("X"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.address)).perform(clearText(),typeText("X"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.primaryB)).perform(click());
        Thread.sleep(500);
        onData(anything()).atPosition(1).perform(click());
        Thread.sleep(1500);

        onView(withId(R.id.province)).perform(click());
        Thread.sleep(500);
        onData(anything()).atPosition(1).perform(click());
        Thread.sleep(1500);

        onView(withId(R.id.submitButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorText2)).check(matches(isDisplayed()));
    }

    @Test
    public void updateInvalidBusy() throws InterruptedException {
        Thread.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).perform(clearText(),typeText("X"), ViewActions.closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.updateButton)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.errorText)).check(matches(isDisplayed()));
    }
}