package com.example.alice.unboundedservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by alice on 21/03/2017.
 */

public class UnboundedService extends Service {
    MediaPlayer mediaplayer;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate()
    {
        mediaplayer=MediaPlayer.create(this, R.raw.firstbloodlolsound);
        mediaplayer.setLooping(false);
    }

    public void onDestroy()
    {
        mediaplayer.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags,
                              int startID){
        mediaplayer.start();
        return START_STICKY;
    }
}
