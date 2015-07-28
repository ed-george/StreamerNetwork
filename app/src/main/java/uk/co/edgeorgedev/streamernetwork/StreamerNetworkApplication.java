package uk.co.edgeorgedev.streamernetwork;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by edgeorge on 26/07/15.
 */
public class StreamerNetworkApplication extends Application {

    public static Context staticContext;

    @Override
    public void onCreate() {
        super.onCreate();
        staticContext = getApplicationContext();

        try {
            Properties prop = new Properties();
            InputStream input = getBaseContext().getAssets().open("parse.properties");

            prop.load(input);

            Parse.initialize(this, prop.getProperty("APPLICATION_ID"), prop.getProperty("CLIENT_ID"));
            ParseInstallation.getCurrentInstallation().saveInBackground();

            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
