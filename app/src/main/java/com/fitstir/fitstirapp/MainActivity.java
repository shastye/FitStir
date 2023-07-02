package com.fitstir.fitstirapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.NavigationUiSaveStateControl;

import com.fitstir.fitstirapp.databinding.ActivityMainBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    @NavigationUiSaveStateControl
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                // Handle for example visibility of menu items
            }

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
                            // TODO: Do the log out

                            Context context = getApplicationContext();
                            Intent intent = getApplicationContext()
                                    .getPackageManager()
                                    .getLaunchIntentForPackage(context.getPackageName());
                            Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
                            context.startActivity(mainIntent);
                            Runtime.getRuntime().exit(0);
                            break;
                        default:
                            pageID = SettingsViewModel.previousPage;
                            break;
                    }

                    try {
                            SettingsViewModel.previousPage = navController.getCurrentDestination().getId();
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
    }
}