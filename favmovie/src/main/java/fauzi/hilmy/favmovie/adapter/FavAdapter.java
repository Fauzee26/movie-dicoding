package fauzi.hilmy.favmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fauzi.hilmy.favmovie.R;

import static fauzi.hilmy.favmovie.db.DatabaseContract.MovieColumns.MOVIE_DATE;
import static fauzi.hilmy.favmovie.db.DatabaseContract.MovieColumns.MOVIE_DESCRIPTION;
import static fauzi.hilmy.favmovie.db.DatabaseContract.MovieColumns.MOVIE_POSTER;
import static fauzi.hilmy.favmovie.db.DatabaseContract.MovieColumns.MOVIE_RATING;
import static fauzi.hilmy.favmovie.db.DatabaseContract.MovieColumns.MOVIE_TITLE;
import static fauzi.hilmy.favmovie.db.DatabaseContract.getColumnString;

public class FavAdapter extends CursorAdapter {
    public FavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            ImageView imgPoster = view.findViewById(R.id.img_poster);
            TextView txtNama = view.findViewById(R.id.edt_namafilm);
            TextView txtDate = view.findViewById(R.id.edt_tglfilm);
            TextView txtRating = view.findViewById(R.id.edt_ratingfilm);
            TextView txtDesc = view.findViewById(R.id.edt_deskripsifilm);

            txtNama.setText(getColumnString(cursor, MOVIE_TITLE));
//            txtDate.setText(getColumnString(cursor, MOVIE_DATE));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(getColumnString(cursor, MOVIE_DATE));
                SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
                String date_release = newDateFormat.format(date);
                txtDate.setText(date_release);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtRating.setText(getColumnString(cursor, MOVIE_RATING));
            txtDesc.setText(getColumnString(cursor, MOVIE_DESCRIPTION));
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/original" + getColumnString(cursor, MOVIE_POSTER))
                    .placeholder(R.drawable.img)
                    .into(imgPoster);
        }
    }
}
