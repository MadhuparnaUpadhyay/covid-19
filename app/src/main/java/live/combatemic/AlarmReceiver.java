package live.combatemic;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import live.combatemic.R;


public class AlarmReceiver extends BroadcastReceiver {
    int mNotificationId = 001;
    public AlarmReceiver() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        try {
            Uri ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, ring);
            r.play();
            sendNotification(context);
//            @SuppressLint("WrongConstant") Notification notification  = new Notification.Builder(context)
//                    .setCategory(Notification.CATEGORY_MESSAGE)
//                    .setContentTitle("title")
//                    .setContentText("text")
//                    .setSmallIcon(R.drawable.ic_help_black_24dp)
//                    .setAutoCancel(true)
//                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                    .build();
//            NotificationManager notificationManager =
//                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//            int oneTimeID = (int) SystemClock.uptimeMillis();
//            notificationManager.notify(oneTimeID, notification );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(Context context){


        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
//        style.bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "101";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX);

            //Configure Notification Channel
            notificationChannel.setDescription("Game Notifications");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Config.title")
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText("Config.content")
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setSmallIcon(R.drawable.ic_help_black_24dp)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX);


        notificationManager.notify(1, notificationBuilder.build());


    }
}