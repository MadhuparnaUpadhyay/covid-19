package com.example.covid_19;

import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {
    int mNotificationId = 001;
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "AlarmReceiver", Toast.LENGTH_LONG).show();
        // Gets an instance of the NotificationManager service
        final NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(context.getResources().getText(R.string.app_name))
                .setContentText("You have new horoscope")
                .setAutoCancel(true); // clear notification after click

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, mNotificationId, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }
}