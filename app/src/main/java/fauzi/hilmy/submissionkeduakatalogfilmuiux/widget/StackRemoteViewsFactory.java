package fauzi.hilmy.submissionkeduakatalogfilmuiux.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.data.Favorite;

import static fauzi.hilmy.submissionkeduakatalogfilmuiux.db.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private ArrayList<Favorite> favorites = new ArrayList<>();
    private Context context;
    private int mAppWidgetId;

    StackRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identity = Binder.clearCallingIdentity();
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        assert cursor != null;
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Favorite favorite = new Favorite(cursor);
            favorites.add(favorite);
        }
        Binder.restoreCallingIdentity(identity);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {

            String IMAGE_PATH = "http://image.tmdb.org/t/p/w780";
            bmp = Glide.with(context)
                    .asBitmap()

                    .load(IMAGE_PATH + favorites.get(position).getBackgroundPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);


        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidgett.EXTRA_ITEM, position);
        extras.putString(ImageBannerWidgett.MOVIE_NAME, favorites.get(position).getMovieName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
