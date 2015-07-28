package uk.co.edgeorgedev.streamernetwork.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import uk.co.edgeorgedev.streamernetwork.classes.MenuListItem;
import uk.co.edgeorgedev.streamernetwork.R;

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

    public static List<MenuListItem> getMenuListItems(){

        List<MenuListItem> temp = new ArrayList<>();

        temp.add(new MenuListItem(R.string.menu_feed, R.drawable.ic_newspaper, false));
        temp.add(new MenuListItem(R.string.menu_streamers, R.drawable.ic_gamepad, false));
        temp.add(new MenuListItem(R.string.menu_forum, R.drawable.ic_account_multiple, true));

        return temp;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
