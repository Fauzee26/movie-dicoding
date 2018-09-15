package fauzi.hilmy.submissionkeduakatalogfilmuiux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterNowUp;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.loader.NowLoader;

public class NowActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.language_button)
    ImageView languageButton;
    @BindView(R.id.recyclerNoww)
    RecyclerView recyclerNoww;
    AdapterNowUp adapterNowUp;
    Bundle bundle = new Bundle();
    @BindView(R.id.drawer_button)
    ImageView drawerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
        ButterKnife.bind(this);
        recyclerNoww.setHasFixedSize(true);
        recyclerNoww.setAdapter(adapterNowUp);

        recyclerNoww.setLayoutManager(new GridLayoutManager(this, 2));
        adapterNowUp = new AdapterNowUp(this);
        adapterNowUp.notifyDataSetChanged();
        recyclerNoww.setAdapter(adapterNowUp);
        getSupportLoaderManager().initLoader(0, bundle, NowActivity.this);
    }

    @OnClick({R.id.drawer_button, R.id.language_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_button:
//                Intent iBack = new Intent(NowActivity.this, MainActivity.class);
//                startActivity(iBack);
//                break;
                finish();
                break;
            case R.id.language_button:
                PopupMenu popup = new PopupMenu(NowActivity.this, languageButton);
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

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new NowLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapterNowUp.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapterNowUp.setData(null);
    }

    @OnClick(R.id.drawer_button)
    public void onViewClicked() {
    }
}
