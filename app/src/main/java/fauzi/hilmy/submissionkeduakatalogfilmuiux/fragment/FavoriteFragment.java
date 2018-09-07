package fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment;


import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterFavorite;

import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.recycler_favorite)
    RecyclerView recyclerFavorite;
    Unbinder unbinder;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.emptylayout)
    RelativeLayout emptylayout;

    private Cursor cursor;
    private AdapterFavorite adapterFavorite;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(getString(R.string.listt));

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        recyclerFavorite.setLayoutManager(layoutManager);
        recyclerFavorite.setHasFixedSize(true);

        adapterFavorite = new AdapterFavorite(getActivity());
        adapterFavorite.setFavorite(cursor);
        recyclerFavorite.setAdapter(adapterFavorite);

        new FavListAsync().execute();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class FavListAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
            emptylayout.setVisibility(View.GONE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
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
