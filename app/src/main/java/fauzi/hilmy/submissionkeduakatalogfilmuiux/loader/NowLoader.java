package fauzi.hilmy.submissionkeduakatalogfilmuiux.loader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;

public class NowLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    private boolean result = false;

    public NowLoader(final Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (result)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(@Nullable final ArrayList<Movie> data) {
        mData = data;
        result = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (result) {
            onReleaseResource(mData);
            mData = null;
            result = false;
        }
    }

    private static final String API_KEY = "3de24acd486b976ff4b5df869947e036";

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movie> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" +
                API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        Log.d("Now Showing", "on success/Now Showing :" + movieItems.getMovieName());
                        Log.d("Now Showing", "on success/Now Showing :" + movieItems.getRating());
                        movieItemses.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), getContext().getString(R.string.cekKoneksi), Toast.LENGTH_SHORT).show();
                Log.e("Error: ", "cause ", error);
            }
        });
        return movieItemses;
    }

    private void onReleaseResource(ArrayList<Movie> data) {

    }
}
