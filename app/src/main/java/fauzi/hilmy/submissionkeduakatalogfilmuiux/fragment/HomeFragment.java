package fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterNowUp;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.MovieAdapter;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
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
    private MovieAdapter adapter;
    public ArrayList<Movie> arrayList;
    String queryy;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    Bundle bundle = new Bundle();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerSearch.setHasFixedSize(true);
        getActivity().setTitle(getResources().getString(R.string.home));

        bundle.putString(EXTRAS_MOVIE, queryy);
        if (queryy != null) {
            getActivity().getSupportLoaderManager().initLoader(0, bundle, HomeFragment.this);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                layoutWait.setVisibility(View.GONE);
                queryy = query;
                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_MOVIE, query);
                recyclerSearch.setAdapter(adapter);

                recyclerSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new MovieAdapter(getActivity());
                adapter.notifyDataSetChanged();
//        adapter.setMovieList(arrayList);
                recyclerSearch.setAdapter(adapter);

//                intentData(recyclerSearch);
//
                getActivity().getSupportLoaderManager().restartLoader(0, bundle, HomeFragment.this);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        menuItem.setActionView(searchView);
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
}
