//package com.example.screens.screens.movies;
//
//import com.example.screens.api.ApiFactory;
//import com.example.screens.api.ApiService;
//import com.example.screens.pojo.Response;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//public class MovieListPresenter {
//
//    private CompositeDisposable compositeDisposable;
//    private MovieListView view;
//    private static String POPULARITY = "popularity.desc";
//    private static String TOP_RATED = "vote_count.desc";
//    private String sorting;
//
//    public MovieListPresenter(MovieListView view) {
//        this.view = view;
//    }
//
//    public void methodOfSort(boolean isTopRated) {
//        if (isTopRated) {
//            sorting = TOP_RATED;
//        }
//        else {
//            sorting = POPULARITY;
//        }
//    }
//
//    public void loadData() {
//        ApiFactory apiFactory = ApiFactory.getInstance();
//        ApiService apiService = apiFactory.getApiService();
//        compositeDisposable = new CompositeDisposable();
//        Disposable disposable = apiService.getResponse("3348f966e6084875c231e59a4f14e5bd", sorting, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Response>() {
//                    @Override
//                    public void accept(Response response) throws Exception {
//                        view.showData(response.getResults());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        view.showError();
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }
//
//    public void disposeDisposable() {
//        if (compositeDisposable != null) {
//            compositeDisposable.dispose();
//        }
//    }
//}
