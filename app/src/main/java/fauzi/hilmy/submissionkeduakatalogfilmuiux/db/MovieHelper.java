package fauzi.hilmy.submissionkeduakatalogfilmuiux.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Favorite;

import static android.provider.BaseColumns._ID;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_DATE;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_DESCRIPTION;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_POSTER;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_POSTER_BACK;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_RATING;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Favorite> query() {
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setMovieName(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                favorite.setMovieDescription(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESCRIPTION)));
                favorite.setMovieDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
                favorite.setBackgroundPoster(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER_BACK)));
                favorite.setMoviePoster(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POSTER)));
                favorite.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(MOVIE_RATING)));

                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Favorite favorite) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MOVIE_TITLE, favorite.getMovieName());
        initialValues.put(MOVIE_RATING, favorite.getRating());
        initialValues.put(MOVIE_DESCRIPTION, favorite.getMovieDescription());
        initialValues.put(MOVIE_POSTER, favorite.getMoviePoster());
        initialValues.put(MOVIE_POSTER_BACK, favorite.getBackgroundPoster());
        initialValues.put(MOVIE_DATE, favorite.getMovieDate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Favorite favorite) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_TITLE, favorite.getMovieName());
        args.put(MOVIE_RATING, favorite.getRating());
        args.put(MOVIE_DESCRIPTION, favorite.getMovieDescription());
        args.put(MOVIE_POSTER, favorite.getMoviePoster());
        args.put(MOVIE_POSTER_BACK, favorite.getBackgroundPoster());
        args.put(MOVIE_DATE, favorite.getMovieDate());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favorite.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null, _ID + " = ?", new String[]{id},
                null, null, null, null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE,
                null, null, null, null, null, _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}