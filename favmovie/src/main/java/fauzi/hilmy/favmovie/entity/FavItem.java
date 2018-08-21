package fauzi.hilmy.favmovie.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import fauzi.hilmy.favmovie.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static fauzi.hilmy.favmovie.db.DatabaseContract.getColumnInt;
import static fauzi.hilmy.favmovie.db.DatabaseContract.getColumnLong;
import static fauzi.hilmy.favmovie.db.DatabaseContract.getColumnString;

public class FavItem implements Parcelable {
    private int id;
    private double rating;
    private String movieName;
    private String movieDescription;
    private String moviePoster;
    private String movieDate;
    private String backgroundPoster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getBackgroundPoster() {
        return backgroundPoster;
    }

    public void setBackgroundPoster(String backgroundPoster) {
        this.backgroundPoster = backgroundPoster;
    }

    public FavItem() {
    }

    public FavItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.movieName = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_TITLE);
        this.movieDate = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_DATE);
        this.moviePoster = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_POSTER);
        this.rating = getColumnLong(cursor, DatabaseContract.MovieColumns.MOVIE_RATING);
        this.backgroundPoster = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_POSTER_BACK);
        this.movieDescription = getColumnString(cursor, DatabaseContract.MovieColumns.MOVIE_DESCRIPTION);
    }

    protected FavItem(Parcel in) {
        id = in.readInt();
        rating = in.readDouble();
        movieName = in.readString();
        movieDescription = in.readString();
        moviePoster = in.readString();
        movieDate = in.readString();
        backgroundPoster = in.readString();
    }

    public static final Creator<FavItem> CREATOR = new Creator<FavItem>() {
        @Override
        public FavItem createFromParcel(Parcel in) {
            return new FavItem(in);
        }

        @Override
        public FavItem[] newArray(int size) {
            return new FavItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(rating);
        dest.writeString(movieName);
        dest.writeString(movieDescription);
        dest.writeString(moviePoster);
        dest.writeString(movieDate);
        dest.writeString(backgroundPoster);
    }
}
