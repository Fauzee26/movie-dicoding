package fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment;

import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.MovieAdapter;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterNowUp;
//import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Upcoming;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.loader.NowLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowShowingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.recycler_now)
    RecyclerView recyclerNow;
    AdapterNowUp adapterNowUp;
    public ArrayList<Movie> arrayList;
    Unbinder unbinder;
    Bundle bundle = new Bundle();

    public NowShowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(false);
        getActivity().setTitle(getResources().getString(R.string.now_showing));
        recyclerNow.setHasFixedSize(true);
        recyclerNow.setAdapter(adapterNowUp);

        recyclerNow.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterNowUp = new AdapterNowUp(getActivity());
        adapterNowUp.notifyDataSetChanged();
        recyclerNow.setAdapter(adapterNowUp);
        getActivity().getSupportLoaderManager().restartLoader(0, bundle, NowShowingFragment.this);


        return view;
    }
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new NowLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        adapterNowUp.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        adapterNowUp.setData(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
