package com.fitstir.fitstirapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.fitstir.fitstirapp.ui.utility.enums.ReminderChannels;
import com.fitstir.fitstirapp.ui.utility.Tags;

import java.util.ArrayList;

public class CheckRecentRun extends Service {
    private final long delay = Tags.MILLISECS_PER_DAY * 2;
    //private final long delay = Tags.MILLISECS_PER_SEC * 3;

    private static int NOTIFICATION_ID = 0;
    private static final ArrayList<Integer> notificationIDs = new ArrayList<Integer>();

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences settings = getSharedPreferences(Tags.TIMED_NOTIFICATION_TAG, MODE_PRIVATE);
        if (MainActivity.areNotificationsAllowed()) {
            if (settings.getLong(Tags.LAST_ON_DESTROY_TAG, Long.MAX_VALUE) + Tags.MILLISECS_PER_SEC < System.currentTimeMillis()) {
                createNotification(
                        this,
                        ReminderChannels.COME_BACK_REMINDERS.getValue(),
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

    private void createNotification(@NonNull Context _context, int _channel_id, int _drawableID, String _title, String _small_content, String _extra_content, int _navID) {
        NavDeepLinkBuilder navBuilder = new NavDeepLinkBuilder(_context.getApplicationContext());
        navBuilder.setComponentName(MainActivity.class);
        navBuilder.setGraph(R.navigation.main_navigation);
        navBuilder.setDestination(_navID);
        PendingIntent pendingIntent = navBuilder.createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, String.valueOf(_channel_id))
                .setSmallIcon(_drawableID)
                .setContentTitle(_title)
                .setContentText(_small_content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(_extra_content))
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(_context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        notificationIDs.add(NOTIFICATION_ID++);
    }
}
