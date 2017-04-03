package com.example.alice.unboundedservice;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Accueil extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);



        final SeekBar sbProg = (SeekBar) findViewById(R.id.sbProg);
        sbProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mp != null)
                    mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final Button start = (Button) findViewById(R.id.bStart);
        final Button stop = (Button) findViewById(R.id.bStop);
        final Button pause = (Button) findViewById(R.id.bPause);

        start.setEnabled(true);
        stop.setEnabled(false);
        pause.setEnabled(false);


        final monThread[] mt = new monThread[1];
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp == null && mt[0] == null) {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.swingdechocobo);
                    mp.setLooping(false);
                    sbProg.setMax(mp.getDuration());
                    mt[0] = new monThread(getApplicationContext(), sbProg, mp);
                    mt[0].execute();
                    start.setEnabled(false);
                    pause.setEnabled(true);
                    stop.setEnabled(true);
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mt[0] != null) {
                    mt[0].cancel(true);
                    mt[0] = null;
                    mp = null;
                }
                sbProg.setProgress(0);
                start.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
            }
        });

        final boolean[] enPause = {false};
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tPause = mp.getCurrentPosition();
                if(enPause[0])
                {
                    mp.seekTo(tPause);
                    mp.start();
                    enPause[0] = false;
                }
                else
                {
                    mp.pause();
                    tPause = mp.getCurrentPosition();
                    enPause[0] = true;

                }
            }
        });

        // audioManager = gestionnaire de son du téléphone
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        // sbSon = SeekBar de gestion du son
        SeekBar sbSon = (SeekBar) findViewById(R.id.sbSon);
        // On met le son au milieu
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2, AudioManager.FLAG_SHOW_UI);
        // J'indique que le maximum de la SeekBar est le maximum de son
        sbSon.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbSon.setProgress(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2);
        sbSon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
