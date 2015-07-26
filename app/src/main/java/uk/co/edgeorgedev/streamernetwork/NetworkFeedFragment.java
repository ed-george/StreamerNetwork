package uk.co.edgeorgedev.streamernetwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.Callback;
import com.pkmmte.pkrss.PkRSS;

import java.util.List;


/**
 * Created by edgeorge on 26/07/15.
 */
public class NetworkFeedFragment extends Fragment implements Callback {

    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        tv = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFeed();
    }

    public void getFeed() {
        PkRSS.with(getActivity()).load("http://www.streamernetwork.com/feed/").nextPage().callback(this).async();
    }

    @Override
    public void OnPreLoad() {

    }

    @Override
    public void OnLoaded(final List<Article> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Article article : list) {
                    tv.append(article.getTitle() + "\n\t" + article.toShortString() + "\n");
                }
            }
        });

    }

    @Override
    public void OnLoadFailed() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.append("Loading failed");
            }
        });
    }
}
