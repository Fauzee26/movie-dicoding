package fauzi.hilmy.submissionkeduakatalogfilmuiux.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterFavorite;

import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.language_button)
    ImageView languageButton;
    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerFavorite;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.emptylayout)
    RelativeLayout emptylayout;
    @BindView(R.id.drawer_button)
    ImageView backButton;

    private Cursor cursor;
    private AdapterFavorite adapterFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        recyclerFavorite.setLayoutManager(new LinearLayoutManager(this));
        recyclerFavorite.setHasFixedSize(true);

        adapterFavorite = new AdapterFavorite(this);
        adapterFavorite.setFavorite(cursor);
        recyclerFavorite.setAdapter(adapterFavorite);

        new FavListAsync().execute();
    }

    @OnClick({R.id.drawer_button, R.id.language_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_button:
//                Intent iBack = new Intent(FavoriteActivity.this, MainActivity.class);
//                startActivity(iBack);
//                break;
                finish();
                break;
            case R.id.language_button:
                PopupMenu popup = new PopupMenu(FavoriteActivity.this, languageButton);
                popup.getMenuInflater()
                        .inflate(R.menu.main, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_language) {
                            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                        }
                        return true;
                    }
                });
                popup.show();
                break;
        }
    }

//    @OnClick(R.id.language_button)
//    public void onViewClicked() {
//        PopupMenu popup = new PopupMenu(FavoriteActivity.this, languageButton);
//        popup.getMenuInflater()
//                .inflate(R.menu.main, popup.getMenu());
//
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.action_language) {
//                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
//                }
//                return true;
//            }
//        });
//        popup.show();
//    }
//
//    @OnClick(R.id.drawer_button)
//    public void onViewClicked() {
//    }

    private class FavListAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            emptylayout.setVisibility(View.GONE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor fav) {
            super.onPostExecute(cursor);
            progress.setVisibility(View.GONE);
            cursor = fav;
            adapterFavorite.setFavorite(cursor);
            adapterFavorite.notifyDataSetChanged();

            if (cursor.getCount() == 0) {
                emptylayout.setVisibility(View.VISIBLE);
                showSnackbarMessage(getString(R.string.nothing_data));
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerFavorite, message, Snackbar.LENGTH_LONG).show();
    }
}
