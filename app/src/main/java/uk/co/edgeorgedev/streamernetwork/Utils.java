package uk.co.edgeorgedev.streamernetwork;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by edgeorge on 26/07/15.
 */
public class Utils {

    private Utils(){}

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri;
        try {
            pm.getPackageInfo("com.facebook.katana", 0);
            //See: http://stackoverflow.com/a/24547437/1048340
            uri = Uri.parse("fb://facewebmodal/f?href=" + url);
        } catch (PackageManager.NameNotFoundException e) {
            uri = Uri.parse(url);
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newTwitterIntent(PackageManager pm, String url, long id) {

        Uri uri;
        try {
            pm.getPackageInfo("com.twitter.android", 0);
            uri = Uri.parse("twitter://user?user_id=" + id);
            Intent intent;
            intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        } catch (PackageManager.NameNotFoundException e) {
            uri = Uri.parse(url);
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }
}
