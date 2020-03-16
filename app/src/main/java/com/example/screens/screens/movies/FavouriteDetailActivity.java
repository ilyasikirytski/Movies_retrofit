package com.example.screens.screens.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.screens.R;
import com.example.screens.api.ApiFactory;
import com.example.screens.data.FavouriteResult;
import com.example.screens.data.MainViewModel;
import com.example.screens.data.Result;
import com.squareup.picasso.Picasso;

public class FavouriteDetailActivity extends AppCompatActivity {

    private ImageView imageViewAddToFavourite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;

    private int id;

    private MainViewModel viewModel;
    private Result result;
    private FavouriteResult favouriteResult;

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
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverView);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        favouriteResult = viewModel.getFavouriteMovieById(id);
        Picasso.get().load(ApiFactory.moviePosterPathBuilder(true, favouriteResult.getPosterPath())).into(imageViewBigPoster);
        textViewTitle.setText(favouriteResult.getTitle());
        textViewOriginalTitle.setText(favouriteResult.getOriginalTitle());
        textViewRating.setText(Double.toString(favouriteResult.getVoteAverage()));
        textViewReleaseDate.setText(favouriteResult.getReleaseDate());
        textViewOverview.setText(favouriteResult.getOverview());
        setFavourite();
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteResult == null) {
            viewModel.insertFavouriteMovieTask(new FavouriteResult(result));
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteResult);
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteResult = viewModel.getFavouriteMovieById(id);
        if (favouriteResult == null ) {
            imageViewAddToFavourite.setImageResource(R.drawable.star_delete);
        } else {
            imageViewAddToFavourite.setImageResource(R.drawable.star_add);
        }
    }
}
