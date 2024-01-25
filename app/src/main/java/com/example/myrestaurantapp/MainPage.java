package com.example.myrestaurantapp;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurantapp.R;
import com.example.myrestaurantapp.adapter.BestFoodsAdapter;
import com.example.myrestaurantapp.adapter.CategoryAdapter;
import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.api.RetrofitClient;
import com.example.myrestaurantapp.databinding.ActivityMainPageBinding;
import com.example.myrestaurantapp.domain.Category;
import com.example.myrestaurantapp.domain.Foods;
import com.example.myrestaurantapp.domain.Location;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private RecyclerView.Adapter adapter, adapter2;

    private RecyclerView recyclerViewCategory, recyclerViewPopularList;

    private BestFoodsAdapter bestFoodsAdapter;
    private CategoryAdapter categoryAdapter;

    private Spinner locationSpinner;
    private List<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainPageBinding binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);
        binding.appBarMainPage.basket.setOnClickListener(view ->
                Snackbar.make(view, "Men basket olacam", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mc_donalds, R.id.nav_mc_donalds, R.id.nav_kfc)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        initLocationSpinner();
        initRecyclerViews();
    }

    private void initLocationSpinner() {
        locationSpinner = findViewById(R.id.locationSp);

        // Fetch locations data from the backend using Retrofit
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Location>> call = apiInterface.getLocations();

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locationList = response.body();

                    // Create an ArrayAdapter using the locations
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, locationList);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Apply the adapter to the spinner
                    locationSpinner.setAdapter(adapter);
                } else {
                    // Handle the error
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // Handle failure
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }

    private void initRecyclerViews() {
        initCategoryRecyclerView();
        initBestFoodsRecyclerView();
    }

    private void initCategoryRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory = findViewById(R.id.recyclerCategory);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        // Create a list of categories (you need to fetch this from the backend)
        List<Category> categories = new ArrayList<>(); // Replace Category with your actual category class

        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void initBestFoodsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerMostFauvorites);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        // Fetch best foods data from the backend using Retrofit
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Foods>> call = apiInterface.getBestFoods();

        call.enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Foods> bestFoodsList = response.body();

                    // Create a BestFoodsAdapter and set it to the recyclerViewPopularList
                    bestFoodsAdapter = new BestFoodsAdapter((ArrayList<Foods>) bestFoodsList);
                    recyclerViewPopularList.setAdapter(bestFoodsAdapter);
                } else {
                    // Handle the error
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Foods>> call, Throwable t) {
                // Handle failure
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
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
