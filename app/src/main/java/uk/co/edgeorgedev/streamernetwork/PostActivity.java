package uk.co.edgeorgedev.streamernetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.pkrss.Article;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.edgeorgedev.streamernetwork.common.Constants;
import uk.co.edgeorgedev.streamernetwork.common.Utils;
import uk.co.edgeorgedev.streamernetwork.view.TagView;

/**
 * Created by edgeorge on 28/07/15.
 */
public class PostActivity extends AppCompatActivity{

    private Article article;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton mShareFab = (FloatingActionButton) findViewById(R.id.fab_share);
        mShareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog();
            }
        });

        Intent intent = getIntent();
        article = intent.getParcelableExtra("article");

        ((TextView) findViewById(R.id.postContent)).setText(getFormattedDescription());

        findViewById(R.id.read_full_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.trackEvents(Constants.READ_FULL_ARTICLE_EVENT);
                startActivity(Utils.openURLIntent(article.getSource().toString()));
            }
        });

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(article.getTitle());

        loadBackdrop((ImageView) findViewById(R.id.backdrop));
        loadTags(article.getTags());
        loadAuthorInfo();

        Utils.trackArticle(article);

    }

    private String getFormattedDescription() {
        String desc = article.getDescription();
        // If there is no description - Show no description text
        if(desc == null || desc.isEmpty()){
            return getString(R.string.no_description);
        }
        // If there is long description, cut off at ellipses
        String fin_desc = desc.substring(0, desc.indexOf("…") + 1);
        // Return full description if cut off doesnt exist
        return fin_desc.isEmpty() ? desc : fin_desc;
    }

    private void loadTags(List<String> tagStrings) {
        TagView tagView = (TagView) findViewById(R.id.tagView);

        TagView.Tag[] tags = new TagView.Tag[tagStrings.size()];
        int i = 0;
        for(String tag : article.getTags()){
            tags[i++] = new TagView.Tag(tag, getResources().getColor(R.color.sn_green));
        }
        tagView.setTags(tags ," ");
    }

    private void loadAuthorInfo(){
        TextView authorInfo = (TextView) findViewById(R.id.author_info);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String formattedDate = formatter.format(new Date(article.getDate()));
        authorInfo.setText(String.format(getString(R.string.written_by), article.getAuthor(), formattedDate));
    }

    private void loadBackdrop(ImageView imageView) {
        Picasso.with(this)
                .load(article.getImage().toString())
                .error(R.drawable.feed_default)
                .into(imageView);
    }


    private void shareDialog(){
        startActivity(
                Utils.getShareDialogIntent(
                        article.getSource().toString(),
                        getString(R.string.share_prefix) + article.getTitle(),
                        getString(R.string.share_post)
                ));
    }
}
