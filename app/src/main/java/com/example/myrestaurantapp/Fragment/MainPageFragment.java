package com.example.myrestaurantapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurantapp.adapter.AllFoodsAdapter;
import com.example.myrestaurantapp.adapter.BestFoodsAdapter;
import com.example.myrestaurantapp.adapter.CategoryAdapter;
import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.api.RetrofitClient;
import com.example.myrestaurantapp.databinding.FragmentMainBinding;
import com.example.myrestaurantapp.domain.Category;
import com.example.myrestaurantapp.domain.Foods;
import com.example.myrestaurantapp.domain.Location;
import com.example.myrestaurantapp.domain.Price;
import com.example.myrestaurantapp.domain.Time;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPageFragment extends Fragment {

    private RecyclerView recyclerViewCategory, recyclerViewPopularList , recyclerViewAllFoods;
    private BestFoodsAdapter bestFoodsAdapter;

    private AllFoodsAdapter allFoodsAdapter;
    private CategoryAdapter categoryAdapter;

    private List<Category> categoryList;

    private FragmentMainBinding binding;
    private Spinner locationSpinner;
    private Spinner timeSpinner;

    private Spinner priceSpinner;

    private List<Location> locationList;
    private List<Time> timeList;

    private List<Price> priceList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize and set up your RecyclerViews here
        initRecyclerViews();
    }

    private void initRecyclerViews() {
        initCategoryRecyclerView();
        initBestFoodsRecyclerView();
        initLocationSpinner();
        initTimeSpinner();
        initPriceSpinner();
        initAllFoodsRecyclerView();
    }
    private void initLocationSpinner() {
        Spinner locationSpinner = binding.locationSp;

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Location>> call = apiInterface.getLocations();

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locationList = response.body();

                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, locationList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    locationSpinner.setAdapter(adapter);
                } else {
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }

    private void initTimeSpinner() {
        Spinner timeSpinner = binding.timeSp;

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Time>> call = apiInterface.getTime();

        call.enqueue(new Callback<List<Time>>() {
            @Override
            public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    timeList = response.body();

                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, timeList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    timeSpinner.setAdapter(adapter);
                } else {
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Time>> call, Throwable t) {
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }

    private void initPriceSpinner() {
        Spinner priceSpinner = binding.priceSp;

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Price>> call = apiInterface.getPrice();

        call.enqueue(new Callback<List<Price>>() {
            @Override
            public void onResponse(Call<List<Price>> call, Response<List<Price>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    priceList = response.body();

                    ArrayAdapter<Price> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, priceList);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    priceSpinner.setAdapter(adapter);
                } else {
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Price>> call, Throwable t) {
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }

    private void initCategoryRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(requireContext(), 4);
        recyclerViewCategory = binding.recyclerCategory;
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Category>> call = apiInterface.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    categoryAdapter = new CategoryAdapter((ArrayList<Category>) categoryList);
                    recyclerViewCategory.setAdapter(categoryAdapter);
                } else {
                    // Handle the error
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Handle failure
                Log.e("Retrofit", "Error: " + t.getMessage());
            }
        });
    }

    private void initBestFoodsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = binding.recyclerMostFauvorites; // Use the binding object to access the RecyclerView
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Foods>> call = apiInterface.getBestFoods();

        call.enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Foods> bestFoodsList = response.body();

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
    private void initAllFoodsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAllFoods = binding.recyclerAllFoods;
        recyclerViewAllFoods.setLayoutManager(linearLayoutManager);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Foods>> call = apiInterface.getAllFoods();

        call.enqueue(new Callback<List<Foods>>() {
            @Override
            public void onResponse(Call<List<Foods>> call, Response<List<Foods>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Foods> allFoodsList = response.body();

                    allFoodsAdapter = new AllFoodsAdapter((ArrayList<Foods>) allFoodsList);
                    recyclerViewAllFoods.setAdapter(allFoodsAdapter);
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
}
