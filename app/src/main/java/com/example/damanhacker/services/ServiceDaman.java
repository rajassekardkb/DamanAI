package com.example.damanhacker.services;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class ServiceDaman extends JobService {
        private static final String TAG = "MyJobService";

        @Override
        public boolean onStartJob(JobParameters params) {
            Log.i(TAG, "Job started");

            // TODO: Add the task you want to perform here

            // Returning true means the job is still running in the background
            return true;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            Log.i(TAG, "Job stopped");

            // Returning true means the job should be rescheduled
            return true;
        }
    }
