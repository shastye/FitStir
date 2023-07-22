package com.fitstir.fitstirapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.NavigationUiSaveStateControl;

import com.fitstir.fitstirapp.databinding.ActivityMainBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.ResetTheme;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    private NotificationManager notificationManager;
    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    private ActivityResultLauncher<String> notificationPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        userAllowedNotifications = result;
    });
    private static boolean userAllowedNotifications = false;
    public static boolean areNotificationsAllowed() { return userAllowedNotifications; }

    private SettingsViewModel settingsViewModel;


    @Override
    @NavigationUiSaveStateControl
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // RESETS THE THEME
        ResetTheme.setInitialTheme(settingsViewModel.getThemeID_inMain());
        ResetTheme.onActivityCreateSetTheme(this);
        //

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_workouts, R.id.navigation_health, R.id.navigation_goals, R.id.navigation_explore)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController, false);

        // SET UP TOOLBAR MENU
        addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.base_toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getTitle() != null) {
                    int pageID = 0;
                    switch (menuItem.getItemId()) {
                        case R.id.profile_item:
                            pageID = R.id.navigation_profile;
                            break;
                        case R.id.settings_item:
                            pageID = R.id.navigation_settings;
                            break;
                        case R.id.log_out_item:
                            FirebaseAuth.getInstance().signOut();
                            Methods.navigateToLogInActivity(getApplicationContext());
                            break;
                        default:
                            pageID = settingsViewModel.getPreviousPage().getValue();
                            break;
                    }

                    try {
                            settingsViewModel.setPreviousPage(navController.getCurrentDestination().getId());;
                            navController.navigate(pageID);
                    } catch (NullPointerException e) {
                        Log.e("Back up error", e.getMessage());
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        //

        settings = getSharedPreferences(Constants.TIMED_NOTIFICATION_TAG, MODE_PRIVATE);
        editor = settings.edit();

        if (userAllowedNotifications) {
            editor.putLong(Constants.LAST_ON_DESTROY_TAG, System.currentTimeMillis());
            editor.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        notificationManager = getSystemService(NotificationManager.class);

        for (int i = 0; i < getNotificationChannels().size(); i++) {
            String name = getNotificationChannels().get(i).first;
            String description = getNotificationChannels().get(i).second;
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel channel = new NotificationChannel(String.valueOf(i), name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setShowBadge(true);

            notificationManager.createNotificationChannel(channel);
        }

        // ASKS USER FOR NOTIFICATION PERMISSIONS
        notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
        //

        notificationManager.cancelAll();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
    }

    @Override
    public void onStop() {
        super.onStop();

        editor.putLong(Constants.LAST_ON_DESTROY_TAG, System.currentTimeMillis());
        editor.commit();

        startService(new Intent(this, CheckRecentRun.class));
    }

    private final ArrayList<Pair<String, String>> getNotificationChannels() {
        return new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>("Reminders", "Reminders to come back."));
        }};
    }
}