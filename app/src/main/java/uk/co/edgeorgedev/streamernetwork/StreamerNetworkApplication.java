package uk.co.edgeorgedev.streamernetwork;

import android.app.Application;
import android.content.Context;

/**
 * Created by edgeorge on 26/07/15.
 */
public class StreamerNetworkApplication extends Application {

    public static Context staticContext;

    @Override
    public void onCreate() {
        super.onCreate();
        staticContext = getApplicationContext();
    }
}
