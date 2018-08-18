package fauzi.hilmy.submissionkeduakatalogfilmuiux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_NAME = "extra_name";
    public static String EXTRA_DESC = "extra_desc";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_POSTER = "extra_poster";
    @BindView(R.id.imgPosterFilm)
    ImageView imgPosterFilm;
    @BindView(R.id.filmNamee)
    TextView txtFilm_name;
    @BindView(R.id.datee)
    TextView txtDate;
    @BindView(R.id.filmoverviewwww)
    TextView txtDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        String desc = getIntent().getStringExtra(EXTRA_DESC);
        String poster = getIntent().getStringExtra(EXTRA_POSTER);
        String release = getIntent().getStringExtra(EXTRA_DATE);

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
                .load("http://image.tmdb.org/t/p/original/" + poster)
                .placeholder(R.drawable.img)
                .into(imgPosterFilm);
    }
}

