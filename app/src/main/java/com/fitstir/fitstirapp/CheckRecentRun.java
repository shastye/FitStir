package com.fitstir.fitstirapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.Tags;

public class CheckRecentRun extends Service {
    private static final long delay = Tags.MILLISECS_PER_DAY * 2;
    //private static final long delay = Tags.MILLISECS_PER_SEC * 3;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences settings = getSharedPreferences(Tags.TIMED_NOTIFICATION_TAG, MODE_PRIVATE);
        if (MainActivity.areNotificationsAllowed()) {
            if (settings.getLong(Tags.LAST_ON_DESTROY_TAG, Long.MAX_VALUE) + Tags.MILLISECS_PER_SEC < System.currentTimeMillis()) {
                Methods.createNotification(
                        this,
                        Tags.Reminder_Channel_ID.COME_BACK_REMINDERS.getValue(),
                        R.drawable.ic_bicep_black_200dp,
                        "Don't forget to check in!",
                        "It's been a few days since you last checked in.",
                        "It's been a few days since you last updated your " +
                                "calorie tracker, diary, or completed a workout. " +
                                "Come back and check in with FitStir to keep up with " +
                                "your mental wellness!",
                        R.id.navigation_goals
                );
            }
        }

        setAlarm();
        stopSelf();
    }

    private void setAlarm() {
        Intent serviceIntent = new Intent(this, CheckRecentRun.class);
        PendingIntent pendingServiceIntent = PendingIntent.getService(
                this,
                131313,
                serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingServiceIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
