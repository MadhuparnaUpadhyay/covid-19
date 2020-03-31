package com.example.covid_19;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AlarmWorker extends Worker {

    public AlarmWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {

        Toast.makeText(getApplicationContext(), "dow ork", Toast.LENGTH_SHORT).show();
        Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
        System.out.println("calling Alarm receiver ");
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        // set unique id to the pending item, so we can call it when needed
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 001, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("" + SystemClock.elapsedRealtime() + "sdba" + AlarmManager.INTERVAL_HALF_HOUR);
//        alarmManager.set(AlarmManager.RTC, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR, pi);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pi);

        //sending work status to caller
        return Worker.Result.success();
    }
}
