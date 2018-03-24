package edu.inspire.messageparser;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;


public class NotificationService extends Service {

    MediaPlayer player;
    Timer timer;
    TimerTask task;
    //Ringtone r;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

       player = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    /*Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();*/
                    player.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer.scheduleAtFixedRate(task, 0, 1000*60*5);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
        timer.cancel();
        // r.stop();

    }
}
