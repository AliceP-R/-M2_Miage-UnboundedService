package com.example.alice.unboundedservice;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.SeekBar;

/**
 * Created by alice on 28/03/2017.
 */

class monThread extends AsyncTask<Void, Integer, Void>
{
    private Context mContext;
    private SeekBar sbProg;
    private MediaPlayer mp;

    monThread(Context mContext, SeekBar sbProg, MediaPlayer mp) {
        this.mContext = mContext;
        this.sbProg = sbProg;
        this.mp = mp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mp.start();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        sbProg.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        while(mp.getCurrentPosition() != mp.getDuration() && !this.isCancelled()) {

            publishProgress(mp.getCurrentPosition());
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        sbProg.setProgress(0);
        mp.stop();
        mp = null;
    }
}