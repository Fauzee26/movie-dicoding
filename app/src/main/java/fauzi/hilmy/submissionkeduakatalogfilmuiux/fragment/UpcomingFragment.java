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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.MovieAdapter;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.adapter.AdapterNowUp;
//import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Upcoming;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.loader.UpcomingLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.recycler_upcoming)
    RecyclerView recyclerUpcoming;
    AdapterNowUp adapterNowUp;
    //    public ArrayList<Movie> arrayList;
    final Bundle bundle = new Bundle();

    Unbinder unbinder;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(getResources().getString(R.string.upcoming));
        recyclerUpcoming.setHasFixedSize(true);
        recyclerUpcoming.setAdapter(adapterNowUp);

        recyclerUpcoming.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterNowUp = new AdapterNowUp(getActivity());
//        adapter.setMovieList(arrayList);
        adapterNowUp.notifyDataSetChanged();
        recyclerUpcoming.setAdapter(adapterNowUp);
        getActivity().getSupportLoaderManager().restartLoader(0, bundle, UpcomingFragment.this);
        return view;
    }

//    private void intentData(RecyclerView recyclerView) {
//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//
//                intent.putExtra(DetailActivity.EXTRA_NAME, arrayList.get(position).getMovieName());
//                intent.putExtra(DetailActivity.EXTRA_DATE, arrayList.get(position).getMovieDate());
//                intent.putExtra(DetailActivity.EXTRA_DESC, arrayList.get(position).getMovieDescription());
//                intent.putExtra(DetailActivity.EXTRA_POSTER, arrayList.get(position).getMoviePoster());
//                intent.putExtra(DetailActivity.EXTRA_RATING, arrayList.get(position).getRating());
//                startActivity(intent);
//
//            }
//        });
//
//    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return new UpcomingLoader(getActivity());
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
