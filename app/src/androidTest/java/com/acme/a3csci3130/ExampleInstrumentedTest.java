package com.acme.a3csci3130;

import android.content.Context;
import android.widget.EditText;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)

public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.acme.a3csci3130", appContext.getPackageName());
    }

    @Rule
    //public ActivityTestRule<MainActivity> menuActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createBusy() throws InterruptedException {
       // onView(withId(R.id.submitButton)).perform(click());
        //Thread.sleep(1500);
        //onView(withId(R.id.button)).perform(click());
        //Thread.sleep(1500);
        //onView(withId(R.id.responseText)).check(matches(withText("Strong!")));
    }
}
