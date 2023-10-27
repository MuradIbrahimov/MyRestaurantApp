package com.example.myrestaurantapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurantapp.adapter.CategoryAdapter;
import com.example.myrestaurantapp.adapter.MostFauvoriteAdapter;
import com.example.myrestaurantapp.databinding.ActivityMainPageBinding;
import com.example.myrestaurantapp.domain.CategoryDomain;
import com.example.myrestaurantapp.domain.FauvoritesDomain;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

private     RecyclerView.Adapter     adapter,adapter2;

private  RecyclerView recyclerViewCategory, recyclerViewPopularList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        com.example.myrestaurantapp.databinding.ActivityMainPageBinding binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);
        binding.appBarMainPage.basket.setOnClickListener(view -> Snackbar.make(view, "Men basket olacam", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mc_donalds, R.id.nav_kfc, R.id.nav_papajohns)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        recyclerViewCategory();
        recyclerViewPopularList();
    }

    private void recyclerViewPopularList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList =findViewById(R.id.recyclerMostFauvorites);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FauvoritesDomain> fauvoritesDomains = new ArrayList<>();
        fauvoritesDomains.add(new FauvoritesDomain("Cheese Burger" , "fast_1" , 9.0 , 20));
        fauvoritesDomains.add(new FauvoritesDomain("Pepperoni pizza" , "fast_2" , 8.0 , 10));
        fauvoritesDomains.add(new FauvoritesDomain("Vegan pizza" , "fast_3" , 7.0 , 15));

        adapter2 = new MostFauvoriteAdapter(fauvoritesDomains);
        recyclerViewPopularList.setAdapter(adapter2);
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
       recyclerViewCategory = findViewById(R.id.recyclerCategory);
       recyclerViewCategory.setLayoutManager(linearLayoutManager);

       ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
        categoryList.add(new CategoryDomain("Burger", "cat_2"));
        categoryList.add(new CategoryDomain("Chicken", "cat_3"));
        categoryList.add(new CategoryDomain("Hotdog", "cat_4"));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategory.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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