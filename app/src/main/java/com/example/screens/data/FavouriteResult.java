package com.example.screens.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_movies")
public class FavouriteResult extends Result{


    @PrimaryKey(autoGenerate = true)
    private int uniqueFavouriteId;

    public int getUniqueFavouriteId() {
        return uniqueFavouriteId;
    }

    public void setUniqueFavouriteId(int uniqueFavouriteId) {
        this.uniqueFavouriteId = uniqueFavouriteId;
    }
//    @SerializedName("popularity")
//    @Expose
//    private double popularity;
//    @SerializedName("vote_count")
//    @Expose
//    private int voteCount;
//    @SerializedName("poster_path")
//    @Expose
//    private String posterPath;
//    @SerializedName("id")
//    @Expose
//    @PrimaryKey
//    private int id;
//    @SerializedName("adult")
//    @Expose
//    private boolean adult;
//    @SerializedName("backdrop_path")
//    @Expose
//    private String backdropPath;
//    @SerializedName("original_language")
//    @Expose
//    private String originalLanguage;
//    @SerializedName("original_title")
//    @Expose
//    private String originalTitle;
//    @SerializedName("title")
//    @Expose
//    private String title;
//    @SerializedName("vote_average")
//    @Expose
//    private double voteAverage;
//    @SerializedName("overview")
//    @Expose
//    private String overview;
//    @SerializedName("release_date")
//    @Expose
//    private String releaseDate;

//    public double getPopularity() {
//        return popularity;
//    }
//
//    public void setPopularity(double popularity) {
//        this.popularity = popularity;
//    }
//
//    public int getVoteCount() {
//        return voteCount;
//    }
//
//    public void setVoteCount(int voteCount) {
//        this.voteCount = voteCount;
//    }
//
//    public boolean isVideo() {
//        return video;
//    }
//
//    public void setVideo(boolean video) {
//        this.video = video;
//    }
//
//    public String getPosterPath() {
//        return posterPath;
//    }
//
//    public void setPosterPath(String posterPath) {
//        this.posterPath = posterPath;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public boolean isAdult() {
//        return adult;
//    }
//
//    public void setAdult(boolean adult) {
//        this.adult = adult;
//    }
//
//    public String getBackdropPath() {
//        return backdropPath;
//    }
//
//    public void setBackdropPath(String backdropPath) {
//        this.backdropPath = backdropPath;
//    }
//
//    public String getOriginalLanguage() {
//        return originalLanguage;
//    }
//
//    public void setOriginalLanguage(String originalLanguage) {
//        this.originalLanguage = originalLanguage;
//    }
//
//    public String getOriginalTitle() {
//        return originalTitle;
//    }
//
//    public void setOriginalTitle(String originalTitle) {
//        this.originalTitle = originalTitle;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public double getVoteAverage() {
//        return voteAverage;
//    }
//
//    public void setVoteAverage(double voteAverage) {
//        this.voteAverage = voteAverage;
//    }
//
//    public String getOverview() {
//        return overview;
//    }
//
//    public void setOverview(String overview) {
//        this.overview = overview;
//    }
//
//    public String getReleaseDate() {
//        return releaseDate;
//    }
//
//    public void setReleaseDate(String releaseDate) {
//        this.releaseDate = releaseDate;
//    }

    public FavouriteResult(double popularity, int voteCount, String posterPath, int id, String backdropPath, String originalLanguage, String originalTitle, String title, double voteAverage, String overview, String releaseDate) {
        super(popularity, voteCount, posterPath, id, backdropPath, originalLanguage, originalTitle, title, voteAverage, overview, releaseDate);
    }

    @Ignore
    public FavouriteResult(Result result) {
        super(result.getPopularity(), result.getVoteCount(), result.getPosterPath(), result.getId(), result.getBackdropPath(), result.getOriginalLanguage(), result.getOriginalTitle(), result.getTitle(), result.getVoteAverage(), result.getOverview(), result.getReleaseDate());
    }
}
