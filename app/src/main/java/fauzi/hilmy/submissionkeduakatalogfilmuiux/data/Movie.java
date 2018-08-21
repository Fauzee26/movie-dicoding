package fauzi.hilmy.submissionkeduakatalogfilmuiux.data;

import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private double rating;
    private String movieName;
    private String movieDescription;
    private String moviePoster;
    private String movieDate;
    private String backgroundPoster;

    public Movie(JSONObject object){
        try {
            int id = object.getInt("id");
            double rating = object.getDouble("vote_average");
            String moviePoster = object.getString("poster_path");
            String movieName = object.getString("title");
            String backPoster = object.getString("backdrop_path");
            String movieDescription = object.getString("overview");
            String movieDate = object.getString("release_date");

            this.id = id;
            this.rating = rating;
            this.moviePoster = moviePoster;
            this.movieName = movieName;
            this.backgroundPoster = backPoster;
            this.movieDescription = movieDescription;
            this.movieDate = movieDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie() {
    }

    public String getBackgroundPoster() {
        return backgroundPoster;
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieDate() {
        return movieDate;
    }
}
