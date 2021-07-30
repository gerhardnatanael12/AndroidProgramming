package id.java.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;


import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationJobService extends JobService {

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Notification manager.
    NotificationManager mNotifyManager;

    private ExecutorService executor;


    /**
     * Called by the system once it determines it is time to run the job.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether or not the job was offloaded to a
     * separate thread.
     * In this case, it is false since the notification can be posted on
     * the main thread.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Handler handler = new Handler(Looper.getMainLooper());
        executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            boolean jobFinished = true;
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                Log.d("Thread", "Thread destroyed");
                jobFinished = false;
            }
            boolean finalJobFinished = jobFinished;
            handler.post(() -> {
                if(finalJobFinished){
                    createNotificationChannel();

                    PendingIntent contentPendingIntent = PendingIntent.getActivity(
                            getApplicationContext(),
                            0,
                            new Intent(getApplicationContext(), MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            getApplicationContext(), PRIMARY_CHANNEL_ID)
                            .setContentTitle("Job Service")
                            .setContentText("Your Job ran to completion")
                            .setContentIntent(contentPendingIntent)
                            .setSmallIcon(R.drawable.ic_job_running)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setAutoCancel(true);

                    mNotifyManager.notify(0, builder.build());
                    Toast.makeText(getApplicationContext(), "Job Complete", Toast.LENGTH_SHORT)
                            .show();
                }
                jobFinished(jobParameters, !finalJobFinished);
            });
        });
        return true;



        // Create the notification channel.
//        createNotificationChannel();

        // Set up the notification content intent to launch the app when
        // clicked.
//        PendingIntent contentPendingIntent = PendingIntent.getActivity
//                (this, 0, new Intent(this, MainActivity.class),
//                        PendingIntent.FLAG_UPDATE_CURRENT);



//        NotificationCompat.Builder builder = new NotificationCompat.Builder
//                (this, PRIMARY_CHANNEL_ID)
//                .setContentTitle(getString(R.string.job_service))
//                .setContentText(getString(R.string.job_running))
//                .setContentIntent(contentPendingIntent)
//                .setSmallIcon(R.drawable.ic_job_running)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setAutoCancel(true);

//        mNotifyManager.notify(0, builder.build());
//        return false;
    }

    /**
     * Called by the system when the job is running but the conditions are no
     * longer met.
     * In this example it is never called since the job is not offloaded to a
     * different thread.
     *
     * @param jobParameters Contains the information about the job.
     * @return Boolean indicating whether the job needs rescheduling.
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (executor != null) {
            executor.shutdownNow();
        }
        Toast.makeText(getApplicationContext(), "Job Failed", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.job_service_notification),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}