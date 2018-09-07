package fauzi.hilmy.submissionkeduakatalogfilmuiux.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.activity.MainActivity;

public class ReminderService extends Service {
    public ReminderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Reminder Service", "Is Running");

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationManager notificationManagerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intentClick = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingClick = PendingIntent.getActivity(this, 0, intentClick, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.cinema)
                .setContentTitle("Info Film")
                .setContentIntent(pendingClick)
                .setContentText("Come back! Movie Catalogue is missing you!")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(0, builder);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
