package fauzi.hilmy.favmovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        //Movie title
        public static String MOVIE_TITLE = "title";
        //Movie description
        public static String MOVIE_DESCRIPTION = "description";
        //Movie date
        public static String MOVIE_DATE = "date";
        //Movie rating
        public static String MOVIE_RATING = "rating";
        //Movie front poster
        public static String MOVIE_POSTER = "posterFront";
        //Movie front poster
        public static String MOVIE_POSTER_BACK = "posterBack";
    }

    public static final String AUTHORITY = "fauzi.hilmy.submissionkeduakatalogfilmuiux";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
