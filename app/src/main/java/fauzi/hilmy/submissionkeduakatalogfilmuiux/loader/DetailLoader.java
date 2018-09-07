package fauzi.hilmy.submissionkeduakatalogfilmuiux.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Detail;

public class DetailLoader extends AsyncTaskLoader<ArrayList<Detail>> {
    private ArrayList<Detail> mData;
    private boolean result = false;
    private String id_movie;

    public DetailLoader(@NonNull Context context, String id_movie) {
        super(context);
        onContentChanged();
        this.id_movie = id_movie;
    }


    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (result)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(@Nullable final ArrayList<Detail> data) {
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

    private void onReleaseResource(ArrayList<Detail> mData) {

    }

    private static final String API_KEY = "3de24acd486b976ff4b5df869947e036";

    @Nullable
    @Override
    public ArrayList<Detail> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Detail> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + id_movie + "?api_key=" +
                API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    for (int i = 0; i < responseObject.length(); i++) {
                        Detail movieItems = new Detail(responseObject);
                        Log.d("By Id", "on Detail :" + movieItems.getRuntime());
                        Log.d("By Id", "on Detail :" + movieItems.getTagline());
                        movieItemses.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               Log.e("Error: ", "cause ", error);
            }
        });
        return movieItemses;
    }
}
