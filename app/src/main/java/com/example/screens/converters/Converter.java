//package com.example.screens.converters;
//
//import androidx.room.TypeConverter;
//
//import com.example.screens.data.Result;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Converter {
//    @TypeConverter
//    public String listMovieToString(List<Result> movies) {
//        return new Gson().toJson(movies);
//    }
//
//    @TypeConverter
//    public List<Result> stringToListMovie(String movieAsString) {
//        Gson gson = new Gson();
//        ArrayList objects = gson.fromJson(movieAsString, ArrayList.class);
//        ArrayList<Result> movies = new ArrayList<>();
//        for (Object o : objects) {
//            movies.add(gson.fromJson(o.toString(), Result.class));
//        }
//        return movies;
//    }
//}
