package fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.MovieAdapter;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.helper.MovPreference;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.loader.MovieLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;
    Unbinder unbinder;
    @BindView(R.id.layoutWait)
    RelativeLayout layoutWait;
    @BindView(R.id.search)
    SearchView search;
    private MovieAdapter adapter;
    static String queryy = "query";
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    Bundle bundle = new Bundle();
//    MovPreference movPreference;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

//        movPreference = new MovPreference(getActivity());
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerSearch.setHasFixedSize(true);

        if (savedInstanceState != null) {
                search.setQuery(savedInstanceState.getString(EXTRAS_MOVIE), true);
//                bundle.getString(EXTRAS_MOVIE);
//                getLoaderManager().initLoader(0, bundle, HomeFragment.this);

        }
        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerSearch.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        recyclerSearch.setLayoutManager(layoutManager);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(getActivity()));

        search.setQueryHint(getResources().getString(R.string.query_hint));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    layoutWait.setVisibility(View.GONE);
                    queryy = query;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_MOVIE, query);
//                    movPreference.setName(queryy);
                    getLoaderManager().restartLoader(0, bundle, HomeFragment.this);
                    hideInputMethod();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Bundle bundle1 = new Bundle();
        bundle1.putString(EXTRAS_MOVIE, search.getQuery().toString().trim());
        getLoaderManager().initLoader(0, bundle1, HomeFragment.this);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Bundle bundleLoad = new Bundle();
        bundleLoad.putString(EXTRAS_MOVIE, search.getQuery().toString().trim());
        getLoaderManager().initLoader(0, bundleLoad, HomeFragment.this);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (movPreference.getName() != null){
            outState.putString(EXTRAS_MOVIE, search.getQuery().toString().trim());
//        }

    }

    public void hideInputMethod() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String movieCollection = "";
        if (args != null) {
            movieCollection = args.getString(EXTRAS_MOVIE);
        }
        return new MovieLoader(getActivity(), movieCollection);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        layoutWait.setVisibility(View.GONE);
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onResume() {
//        if (movPreference.getName() != null) {
//            Bundle bundle1 = new Bundle();
//            bundle1.putString(EXTRAS_MOVIE, movPreference.getName());
//            getLoaderManager().restartLoader(0, bundle1, HomeFragment.this);
//        } else {
//            super.onResume();
//        }
//    }

    @Override
    public void onDestroy() {
//        movPreference.setName(null);
        super.onDestroy();
    }
}
