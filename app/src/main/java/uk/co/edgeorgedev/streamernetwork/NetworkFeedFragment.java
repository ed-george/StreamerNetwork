package uk.co.edgeorgedev.streamernetwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFeed();
    }

    public void getFeed() {
        PkRSS.with(getActivity()).load("http://www.streamernetwork.com/feed/").callback(this).page(0).async();
    }

    @Override
    public void OnPreLoad() {

    }

    @Override
    public void OnLoaded(final List<Article> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new FrontPageAdapter(getActivity(), list));
            }
        });

    }

    @Override
    public void OnLoadFailed() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
            }
        });
    }
}
