package fauzi.hilmy.submissionkeduakatalogfilmuiux;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract;

import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_DESC = "extra_desc";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_POSTER_BACK = "back";

    @BindView(R.id.imgPosterFilm)
    ImageView imgPosterFilm;
    @BindView(R.id.filmNamee)
    TextView txtFilm_name;
    @BindView(R.id.datee)
    TextView txtDate;
    @BindView(R.id.filmoverviewwww)
    TextView txtDesc;
    @BindView(R.id.fabFavorite)
    FloatingActionButton fabFavorite;

    String name, desc, poster, release, rate, poster_back;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        fabFavorite.setImageResource(R.drawable.ic_fav_not);
        name = getIntent().getStringExtra(EXTRA_NAME);
        desc = getIntent().getStringExtra(EXTRA_DESC);
        poster = getIntent().getStringExtra(EXTRA_POSTER);
        release = getIntent().getStringExtra(EXTRA_DATE);
        rate = getIntent().getStringExtra(EXTRA_RATING);
        poster_back = getIntent().getStringExtra(EXTRA_POSTER_BACK);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(release);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String date_release = newDateFormat.format(date);
            txtDate.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtFilm_name.setText(name);
        txtDesc.setText(desc);
//        datee.setText(release);

        Picasso.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/original" + poster)
                .placeholder(R.drawable.img)
                .into(imgPosterFilm);

//        setFavorite();
    }

    public boolean setFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra(EXTRA_NAME))) {
                    fabFavorite.setImageResource(R.drawable.ic_fav_true);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }
        return favorite;
    }

    public void favorite() {
        if (setFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            getContentResolver().delete(uri, null, null);
            fabFavorite.setImageResource(R.drawable.ic_fav_not);
            toast(getString(R.string.deleted));
        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.MovieColumns.MOVIE_TITLE, name);
            values.put(DatabaseContract.MovieColumns.MOVIE_POSTER, poster);
            values.put(DatabaseContract.MovieColumns.MOVIE_DATE, release);
            values.put(DatabaseContract.MovieColumns.MOVIE_DESCRIPTION, desc);
            values.put(DatabaseContract.MovieColumns.MOVIE_RATING, rate);
            values.put(DatabaseContract.MovieColumns.MOVIE_POSTER_BACK, poster_back);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            fabFavorite.setImageResource(R.drawable.ic_fav_true);
            toast(getString(R.string.added));
        }
    }

    @OnClick(R.id.fabFavorite)
    public void onViewClicked() {
        favorite();
    }

    private void toast(String message) {
        Toast.makeText(this, name + message, Toast.LENGTH_SHORT).show();
    }
}

