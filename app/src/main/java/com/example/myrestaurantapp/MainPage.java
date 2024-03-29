package com.example.myrestaurantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myrestaurantapp.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationView;

public class MainPage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainPageBinding binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main_page, R.id.nav_mc_donalds, R.id.nav_kfc, R.id.nav_profile, R.id.nav_register)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        if (userHasAdminRole()) {
            showAdminMenuItems(navigationView);
        }
    }

    private void showAdminMenuItems(NavigationView navigationView) {
        Menu navMenu = navigationView.getMenu();

        // Find the header view
        View headerView = navigationView.getHeaderView(0);

        // Find the TextView inside the header view
        TextView headerRoleTxt = headerView.findViewById(R.id.headerRoleTxt);
        TextView headerRoleMessage = headerView.findViewById(R.id.headerRoleMessage);
        MenuItem registerItem = navMenu.findItem(R.id.nav_register);
        if (registerItem != null) {
            registerItem.setVisible(true);
            headerRoleTxt.setText("ADMIN");
            headerRoleMessage.setText("Qazanmaqdan zövq al!");
        }
    }

    private boolean userHasAdminRole() {
        // Retrieve user role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("role", "");

        // Check if the user has the admin role
        return userRole.equals("ROLE_ADMIN");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
