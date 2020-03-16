package com.example.screens.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.screens.adapters.MovieAdapter;
import com.example.screens.api.ApiFactory;
import com.example.screens.api.ApiService;
import com.example.screens.pojo.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private MovieAdapter adapter;
    private LiveData<List<Result>> movies;
    private LiveData<List<FavouriteResult>> favouriteMovies;
    private CompositeDisposable compositeDisposable;
    private String sorting;
    private int thisPage = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(application);
        movies = database.movieDao().getAllMovies();
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
        favouriteMovies = database.movieDao().sortByDescId();
    }

    public void deleteAllMovies() {
        new DeleteMoviesTask().execute();
    }

    private static class DeleteMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... integers) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    public LiveData<List<FavouriteResult>> getFavouriteMovies() {
        return favouriteMovies;
    }

    @SuppressWarnings("unchecked")
    public void insertMovieTask(List<Result> result) {
        new InsertTask().execute(result);
    }

    private static class InsertTask extends AsyncTask<List<Result>, Void, Void> {
        @Override
        protected Void doInBackground(List<Result>... results) {
            if (results != null && results.length > 0) {
                database.movieDao().insertMovie(results[0]);
            }
            return null;
        }
    }

    public void insertFavouriteMovieTask(FavouriteResult result) {
        new InsertFavouriteTask().execute(result);
    }

    private static class InsertFavouriteTask extends AsyncTask<FavouriteResult, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteResult... results) {
            if (results != null && results.length > 0) {
                database.movieDao().insertFavouriteMovie(results[0]);
            }
            return null;
        }
    }

    public void deleteMovie(Result result) {
        new DeleteTask().execute(result);
    }

    private static class DeleteTask extends AsyncTask<Result, Void, Void> {
        @Override
        protected Void doInBackground(Result... results) {
            database.movieDao().deleteMovie(results[0]);
            return null;
        }
    }

    public void deleteFavouriteMovie(FavouriteResult result) {
        new DeleteFavouriteTask().execute(result);
    }

    private static class DeleteFavouriteTask extends AsyncTask<FavouriteResult, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteResult... results) {
            if (results != null && results.length > 0)
                database.movieDao().deleteFavouriteMovie(results[0]);
            return null;
        }
    }

    public Result getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteResult getFavouriteMovieById(int id) {
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, Result> {
        @Override
        protected Result doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteResult> {
        @Override
        protected FavouriteResult doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }


    public LiveData<List<Result>> getMovies() {
        return movies;
    }

    public void methodOfSort(boolean isTopRated) {
        if (isTopRated) {
            sorting = "vote_average.desc";
        } else {
            sorting = "popularity.desc";
        }
//        thisPage = 1;
//        deleteAllMovies();
        loadData();
    }

    public void startPage(){
//        deleteAllMovies();
        thisPage = 1;
//        loadData();
    }

//    public static class JSONLoader extends AsyncTaskLoader<JSONObject> {
//
//        private Bundle bundle;
//
//        public JSONLoader(@NonNull Context context, Bundle bundle) {
//            super(context);
//            this.bundle = bundle;
//        }
//
//        @Override
//        protected void onStartLoading() {
//            super.onStartLoading();
//            forceLoad();
//        }
//
//        @Nullable
//        @Override
//        public JSONObject loadInBackground() {
//            if (bundle == null) {
//                return null;
//            }
//            String urlAsString = bundle.getString("url");
//            URL url = null;
//            try {
//                url = new URL(urlAsString);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            JSONObject result = null;
//            if (url == null){
//                return null;
//            }
//            HttpURLConnection connection = null;
//            try {
//                connection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                StringBuilder builder = new StringBuilder();
//                String line = reader.readLine();
//                while (line != null) {
//                    builder.append(line);
//                    line = reader.readLine();
//                }
//                result = new JSONObject(builder.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            }
//            return result;
//        }
//    }


    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getResponse("3348f966e6084875c231e59a4f14e5bd", "ru", sorting, 1000, thisPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
//                        deleteAllMovies();
                        insertMovieTask(response.getResults());
//                        adapter.addMovies(response.getResults());

                        thisPage++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onCleared();
    }
}