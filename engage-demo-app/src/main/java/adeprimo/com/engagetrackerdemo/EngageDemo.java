package adeprimo.com.engagetrackerdemo;

import android.app.Application;
import android.os.StrictMode;

import adeprimo.com.tuloengagetracker.Tracker;
import adeprimo.com.tuloengagetracker.utils.LogLevel;

public class EngageDemo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()   // detectDiskReads, detectDiskWrites, detectNetwork
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
*/
        Tracker.init(new Tracker.TrackerBuilder("wot", "http://10.0.2.2:8080/api/v1/events", this.getApplicationContext())
                .productId("PEIWEI")
                .level(LogLevel.VERBOSE)
                .build()
        );
    }
}
