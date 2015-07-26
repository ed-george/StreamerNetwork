package uk.co.edgeorgedev.streamernetwork;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.pkrss.Article;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by edgeorge on 28/01/15.
 */
public class FrontPageAdapter extends RecyclerView.Adapter<FrontPageAdapter.ViewHolder> {
    private List<Article> articleList;
    private Activity ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleText;
        public TextView mCommentText;
        public TextView mCreatedDate;
        public ImageView mMainImage;
        public ImageView mCommentImage;
        public TextView mFeaturedView;
        public ViewHolder(View v) {
            super(v);
            mTitleText = (TextView) v.findViewById(R.id.info_text);
            mCommentText = (TextView) v.findViewById(R.id.comment_number_text);
            mCreatedDate = (TextView) v.findViewById(R.id.created_text);
            mMainImage = (ImageView) v.findViewById(R.id.feature_image);
            mCommentImage = (ImageView) v.findViewById(R.id.comment_image);
            mFeaturedView = (TextView) v.findViewById(R.id.featured_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FrontPageAdapter(Activity ctx, List<Article> articleList) {
        this.ctx = ctx;
        this.articleList = articleList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FrontPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_card_view, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.mFeaturedView.setText((article.getTags() != null ? article.getTags().get(0) : ctx.getString(R.string.app_name)));
        holder.mTitleText.setText(article.getTitle());
        Picasso.with(ctx).load(article.getImage()).error(R.drawable.feed_default).into(holder.mMainImage);

        holder.mMainImage.setOnClickListener(goToPostActivity(holder, article));

        holder.mCommentText.setText(String.format(ctx.getString(R.string.posted_by), article.getAuthor()));

        DateTime articleTime = new DateTime(article.getDate());
        DateTime now = new DateTime();
        Period period = new Period(articleTime, now);

        holder.mCreatedDate.setText(ctx.getResources().getQuantityString(R.plurals.numberOfDays, period.getDays(), period.getDays()));

//        if(article.isTrending()){
//            holder.mCommentImage.setImageResource(R.drawable.trending);
//        }

    }

    private View.OnClickListener goToPostActivity(final ViewHolder holder, final Article post) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PostActivity.launch(ctx, holder.mMainImage, post);
            }
        };
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

}
