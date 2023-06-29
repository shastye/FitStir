package com.fitstir.fitstirapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fitstir.fitstirapp.databinding.ActivityConnectBinding;
import com.google.android.material.navigation.NavigationView;

public class ConnectActivity extends AppCompatActivity {

    private ActivityConnectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConnectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navView = findViewById(R.id.nav_view_connect);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_initial)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_connect);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewConnect, navController);
    }

}