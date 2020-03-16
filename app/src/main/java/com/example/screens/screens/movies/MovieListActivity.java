package com.example.screens.screens.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screens.R;
import com.example.screens.adapters.MovieAdapter;
import com.example.screens.data.MainViewModel;
import com.example.screens.data.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textViewTopRated;
    private TextView textViewPopularity;

    private MainViewModel viewModel;
    private boolean isLoading = false;
    private int page = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MovieListActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        viewModel.getMovies().observe(this, new Observer<List<Result>>() {
//            @Override
//            public void onChanged(List<Result> results) {
//                movieAdapter.setResults(results);
//            }
//        });
        setContentView(R.layout.activity_main);
        switchSort = findViewById(R.id.switchSort);
        textViewTopRated = findViewById(R.id.textViewTopRated);
        textViewPopularity = findViewById(R.id.textViewPopularity);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        movieAdapter = new MovieAdapter();
        movieAdapter.setResults(new ArrayList<Result>());
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMovies.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMethodOfSort(isChecked);
            }
        });
        switchSort.setChecked(false);
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Result result = movieAdapter.getResults().get(position);
                Intent intent = new Intent(MovieListActivity.this, DetailActivity.class);
                intent.putExtra("id", result.getId());
                startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
//                if (!isLoading) {
                    Toast.makeText(MovieListActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
//                }
                viewModel.loadData();
            }
        });
        viewModel.getMovies().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                movieAdapter.setResults(results);
                isLoading = false;
            }
        });

    }

//    public void showError() {
//        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//    }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onCLickSetTopRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    public void setMethodOfSort(boolean isChecked) {
        if (isChecked) {
            viewModel.methodOfSort(true);
            textViewTopRated.setTextColor(getColor(R.color.colorAccent));
            textViewPopularity.setTextColor(getColor(R.color.colorWhite));
        } else {
            viewModel.methodOfSort(false);
            textViewTopRated.setTextColor(getColor(R.color.colorWhite));
            textViewPopularity.setTextColor(getColor(R.color.colorAccent));
        }
    }
}
