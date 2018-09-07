package fauzi.hilmy.submissionkeduakatalogfilmuiux.release;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Movie;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.UpcomingFragment;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ReleaseService extends JobService {

    public static final String TAG = ReleaseService.class.getSimpleName();
    private static final String API_KEY = "3de24acd486b976ff4b5df869947e036";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() executed");
        getRelease(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void getRelease(JobParameters params) {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                ArrayList<Movie> data = new ArrayList<>();

                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray jsonArray = object.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Movie movie = new Movie(jsonObject);
                        data.add(movie);
                    }

                    Date todayDate = Calendar.getInstance().getTime();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(todayDate);

                    for (int i = 0; i < data.size(); i++) {
                        if (date.equals(data.get(i).getMovieDate())) {
                            showNotification(getApplicationContext(), data.get(i).getMovieName() + " is released today!", i + 1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void showNotification(Context context, String message, int notifId) {
        Intent intent = new Intent(this.getApplicationContext(), UpcomingFragment.class);
        PendingIntent pendingClick = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Today's Releases")
                .setSmallIcon(R.drawable.cinema)
                .setContentText(message)
                .setContentIntent(pendingClick)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        manager.notify(notifId, builder.build());
    }

}
