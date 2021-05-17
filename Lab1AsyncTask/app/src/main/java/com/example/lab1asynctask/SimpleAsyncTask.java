package com.example.lab1asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    // The weak reference prevents the memory leak by allowing the object held by that reference to be garbage collected if necessary (when Activity is destroyed).
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> progressBar;
    private static final int CHUNK_SIZE = 5;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        progressBar = new WeakReference<>(pb);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random random = new Random();

        int number = random.nextInt(11);
        int milli = number * 200;
        int chunkSize = milli / CHUNK_SIZE;

        // This will update the progress bar 5 times (CHUNK_SIZE)
        for(int i = 0; i < CHUNK_SIZE; i++) {
            // Sleep for the random amount of time
            try {
                Thread.sleep(chunkSize);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress(((i + 1) * 100) / CHUNK_SIZE);
        }

        return "Awake at last after sleeping for " + milli + " milliseconds!";
    }

    // Called by publishProgress() in doInBackground()
    // Updates the progress bar
    protected void onProgressUpdate(Integer... progress) {
        progressBar.get().setProgress(progress[0]);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
