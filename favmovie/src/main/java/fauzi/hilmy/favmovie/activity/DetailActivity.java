package fauzi.hilmy.favmovie.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import fauzi.hilmy.favmovie.R;
import fauzi.hilmy.favmovie.entity.FavItem;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgPosterFilm)
    ImageView imgPosterFilm;
    @BindView(R.id.filmNamee)
    TextView txtTitle;
    @BindView(R.id.datee)
    TextView txtDate;
    @BindView(R.id.filmoverviewwww)
    TextView txtDesc;

    private FavItem favItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) favItem = new FavItem(cursor);
                cursor.close();
            }
        }

        txtTitle.setText(favItem.getMovieName());
        txtDesc.setText(favItem.getMovieDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(favItem.getMovieDate());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
            String date_release = newDateFormat.format(date);
            txtDate.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/original" + favItem.getMoviePoster())
                .placeholder(R.drawable.img)
                .error(R.drawable.error)
                .into(imgPosterFilm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.action_delete) {
            showDeleteAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteAlert() {
        String dialogMessage = "Apakah anda yakin akan menghapus item ini dari list film favorit anda?";
        String dialogTitle = "Hapus List Favorit";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setIcon(R.drawable.delete)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = getIntent().getData();
                        getContentResolver().delete(uri, null, null);
                        Toast.makeText(DetailActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        if (!DetailActivity.this.isFinishing()){
            alertDialog.show();
        }    }
}
