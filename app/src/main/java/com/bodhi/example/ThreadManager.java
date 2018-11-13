package com.bodhi.example;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qq on 2017/9/5.
 */

public class ThreadManager {

    private static ThreadManager threadManager;

    public static ThreadManager getInstance() {
        if (threadManager == null) {
            threadManager = new ThreadManager();
        }
        return threadManager;
    }

    private ScheduledExecutorService scheduledExecutorService;


    public void run(Runnable runnable) {
        if (scheduledExecutorService == null)
            scheduledExecutorService = Executors.newScheduledThreadPool(10);

        if (runnable == null)
            return;

        scheduledExecutorService.submit(runnable);
    }

    public void runDelayed(Runnable runnable, long delay) {
        if (scheduledExecutorService == null)
            scheduledExecutorService = Executors.newScheduledThreadPool(10);

        if (runnable == null)
            return;

        scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }


    public void post(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delay) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, delay);
    }
}
