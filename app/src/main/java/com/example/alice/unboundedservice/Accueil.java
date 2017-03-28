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

        mp=MediaPlayer.create(this, R.raw.swingdechocobo);
        mp.setLooping(false);

        final SeekBar sbProg = (SeekBar) findViewById(R.id.sbProg);
        sbProg.setMax(mp.getDuration());
        sbProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mp.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button start = (Button) findViewById(R.id.bStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                monThread mt = new monThread(getApplicationContext(), sbProg, mp);
                mt.execute();
            }
        });

        Button stop = (Button) findViewById(R.id.bStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                sbProg.setProgress(0);
            }
        });

        Button pause = (Button) findViewById(R.id.bPause);
        final boolean[] enPause = {false};
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enPause[0])
                {
                    mp.start();
                }
                else
                {
                    mp.pause();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
