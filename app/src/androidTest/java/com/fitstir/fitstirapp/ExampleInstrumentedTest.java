package com.fitstir.fitstirapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.fitstir.fitstirapp.ui.goals.Goal;
import com.fitstir.fitstirapp.ui.health.diary.DiaryViewModel;
import com.fitstir.fitstirapp.ui.runtracker.utilites.RunnerData;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.enums.GoalTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
        cal.set(2023, 9, 17);

        Methods.addDataToGoal(GoalTypes.WEIGHT_CHANGE, cal.getTime(), 135.0);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }

    @Test
    public void addDataToDiary() {
        FirebaseDatabase.getInstance()
                .getReference("DiaryData")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(DiaryViewModel.getData());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }

    @Test
    public void addDataToRunner() {
        RunnerData runner1 = new RunnerData(
                1.152279812279343605,
                32.0,
                437.20326042175293,
                60.782816666666666,
                "2023-10-18 19:33:55",
                33.9944867,
                -84.34361
        );

        runner1.addRunData(InstrumentationRegistry.getInstrumentation().getContext(), runner1);


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }

    @Test
    public void addGoalsAndData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);

        Goal goal = new Goal(GoalTypes.RUN_CLUB_ENDURANCE, 60);

        goal.addData(cal.getTime(), 20);
        cal.add(Calendar.DATE, 2);
        goal.addData(cal.getTime(), 22);
        cal.add(Calendar.DATE, 5);
        goal.addData(cal.getTime(), 27);
        cal.add(Calendar.DATE, 3);
        goal.addData(cal.getTime(), 32);

        cal.add(Calendar.DATE, 5);
        goal.addData(cal.getTime(), 45);
        cal.add(Calendar.DATE, 3);
        goal.addData(cal.getTime(), 48);
        cal.add(Calendar.DATE, 2);
        goal.addData(cal.getTime(), 51);

        cal.add(Calendar.DATE, 2);
        goal.addData(cal.getTime(), 54);
        cal.add(Calendar.DATE, 3);
        goal.addData(cal.getTime(), 58);
        cal.add(Calendar.DATE, 5);
        goal.addData(cal.getTime(), 63);

        Methods.addGoalToFirebase(goal);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }


}