package com.fitstir.fitstirapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.NavigationUiSaveStateControl;

import com.fitstir.fitstirapp.databinding.ActivityMainBinding;
import com.fitstir.fitstirapp.ui.settings.SettingsViewModel;
import com.fitstir.fitstirapp.ui.settings.fragments.ProfileFragment;
import com.fitstir.fitstirapp.ui.utility.CheckRecentRun;
import com.fitstir.fitstirapp.ui.utility.Constants;
import com.fitstir.fitstirapp.ui.utility.Methods;
import com.fitstir.fitstirapp.ui.utility.classes.IOnBackPressed;
import com.fitstir.fitstirapp.ui.utility.classes.ResetTheme;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

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
    private GoogleSignInClient client;
    private GoogleSignInOptions options;


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

        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_workouts, R.id.navigation_health, R.id.navigation_goals)
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
                            pageID = R.id.log_out_item;
                            break;
                        default:
                            pageID = settingsViewModel.getPreviousPage().getValue();
                            break;
                    }

                    try {
                        return doBackUp(pageID);
                    } catch (IllegalArgumentException e) {
                        Log.e("Back up error", e.getMessage());
                        return true;
                    }
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

        //access firebase storage for profile pic
        try{
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            String user = auth.getCurrentUser().getUid();
            StorageReference photo = storageReference.child("images/"+user);
            photo.getBytes(Constants.MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    settingsViewModel.setAvatar(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        catch (NullPointerException e){

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
        return doBackUp(settingsViewModel.getPreviousPage().getValue());
    }

    public boolean doBackUp(int pageID) {
        if (pageID != 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main).getChildFragmentManager().getPrimaryNavigationFragment();
            if (!(fragment instanceof IOnBackPressed)) {
                if (pageID == R.id.log_out_item) {
                    signOut();
                } else {
                    settingsViewModel.setPreviousPage(navController.getCurrentDestination().getId());
                    navController.navigate(pageID);
                }
                return false;
            } else {
                if (pageID == R.id.navigation_profile) {
                    navController.navigate(pageID);
                    return false;
                } else {
                    return ((IOnBackPressed) fragment).onBackPressed();
                }
            }
        } else {
            return Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
        }
    }

    public void signOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(MainActivity.this, options);

        if(client != null || user != null){
            auth.signOut();
            Auth.GoogleSignInApi.signOut(client.asGoogleApiClient()).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            Methods.navigateToLogInActivity(getApplicationContext());
                        }
                    });
            client.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Methods.navigateToLogInActivity(getApplicationContext());
                }
            });
        }
        else if (client == null && user == null) {
            Methods.navigateToLogInActivity(getApplicationContext());
        }
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