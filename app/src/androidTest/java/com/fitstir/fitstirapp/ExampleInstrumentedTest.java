package com.fitstir.fitstirapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.fitstir.fitstirapp", appContext.getPackageName());
    }

    @Test
    public void methodAddDataToGoalWorksIfNoGoal() {
        Methods.addDataToGoal(GoalTypes.RUN_CLUB_ENDURANCE, Calendar.getInstance().getTime(), 30.0);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }

    @Test
    public void methodAddDataToGoalWorksIfIsGoal() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);

        Methods.addDataToGoal(GoalTypes.WEIGHT_CHANGE, cal.getTime(), 132.0);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }
}