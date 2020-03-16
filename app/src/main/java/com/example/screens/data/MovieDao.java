package com.example.screens.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Result>> getAllMovies();

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<FavouriteResult>> getAllFavouriteMovies();

    @Query("SELECT * FROM movies WHERE id == :movieId")
    Result getMovieById(int movieId);

    @Query("SELECT * FROM favourite_movies WHERE id == :movieId")
    FavouriteResult getFavouriteMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Insert
    void insertMovie(List<Result> result);

    @Delete
    void deleteMovie(Result result);

    @Insert()
    void insertFavouriteMovie(FavouriteResult result);

    @Delete
    void deleteFavouriteMovie(FavouriteResult result);

    @Query("SELECT * FROM favourite_movies ORDER BY uniqueFavouriteId DESC")
    LiveData<List<FavouriteResult>> sortByDescId();
}
