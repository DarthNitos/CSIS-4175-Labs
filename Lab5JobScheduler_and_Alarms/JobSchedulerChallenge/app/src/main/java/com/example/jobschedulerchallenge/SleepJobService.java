package com.example.jobschedulerchallenge;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.widget.Toast;

public class SleepJobService extends JobService {
    JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters params) {
        jobParameters = params;

        new MyAsyncTask().execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, getText(R.string.job_interrupted), Toast.LENGTH_LONG).show();

        return true;
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (jobParameters!= null) {
                jobFinished(jobParameters, false);
            }
        }
    }
}
