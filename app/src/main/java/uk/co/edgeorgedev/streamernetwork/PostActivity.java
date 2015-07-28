package uk.co.edgeorgedev.streamernetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by edgeorge on 28/07/15.
 */
public class PostActivity extends AppCompatActivity{

    private String imageUrl, postTitle, postData, postUrl;
    private FloatingActionButton mShareFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mShareFab = (FloatingActionButton) findViewById(R.id.fab_share);
        mShareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostInBrowser();
            }
        });

        Intent intent = getIntent();
        postTitle = intent.getStringExtra("title");
        postUrl = intent.getStringExtra("postUrl");
        imageUrl = intent.getStringExtra("imageUrl");
        postData = intent.getStringExtra("postData");

        Log.w(getClass().getCanonicalName(), postTitle + " " + imageUrl + " " + postData);

        ((TextView) findViewById(R.id.postContent)).setText(postData);


        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(postTitle);

        loadBackdrop((ImageView) findViewById(R.id.backdrop));
    }

    private void loadBackdrop(ImageView imageView) {
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.feed_default)
                .error(R.drawable.feed_default)
                .fit()
                .into(imageView);
    }


    private void openPostInBrowser(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, postUrl);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_prefix) + postTitle);
        startActivity(Intent.createChooser(intent, getString(R.string.share_post)));
    }

}
