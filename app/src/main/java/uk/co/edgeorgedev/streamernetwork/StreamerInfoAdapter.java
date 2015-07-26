package uk.co.edgeorgedev.streamernetwork;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.pkrss.Article;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import uk.co.edgeorgedev.streamernetwork.twitch.models.TwitchChannel;

/**
 * Created by edgeorge on 28/01/15.
 */
public class StreamerInfoAdapter extends RecyclerView.Adapter<StreamerInfoAdapter.ViewHolder> {
    private List<TwitchChannel> twitchChannelList;
    private Activity ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleText;
        public TextView mCommentText;
        public TextView mCreatedDate;
        public ImageView mMainImage;
        public ImageView mCommentImage;
        public TextView mFeaturedView;
        public TextView mTitleSubText;
        public View mFeaturedBoxView;
        public ViewHolder(View v) {
            super(v);
            mTitleText = (TextView) v.findViewById(R.id.info_text);
            mTitleText.setTextSize(18);
            mCommentText = (TextView) v.findViewById(R.id.comment_number_text);
            mCreatedDate = (TextView) v.findViewById(R.id.created_text);
            mMainImage = (ImageView) v.findViewById(R.id.feature_image);
            mCommentImage = (ImageView) v.findViewById(R.id.comment_image);
            mFeaturedView = (TextView) v.findViewById(R.id.featured_layout);
            mTitleSubText = (TextView) v.findViewById(R.id.info_sub_text);
            mFeaturedBoxView = v.findViewById(R.id.featured_box_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StreamerInfoAdapter(Activity ctx, List<TwitchChannel> articleList) {
        this.ctx = ctx;
        Collections.sort(articleList);
        this.twitchChannelList = articleList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StreamerInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_card_view, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TwitchChannel twitchChannel = twitchChannelList.get(position);

        holder.mFeaturedView.setText(twitchChannel.getStatus());
        holder.mTitleText.setText(twitchChannel.getName());
        Picasso.with(ctx).load(twitchChannel.getImage().getSize600()).error(R.drawable.feed_default).into(holder.mMainImage);

        //holder.mMainImage.setOnClickListener(goToPostActivity(holder, twitchChannel));

        holder.mCommentText.setText(MessageFormat.format(ctx.getString(R.string.follower_count),twitchChannel.getFollowersCount()));

        holder.mCreatedDate.setText(MessageFormat.format(ctx.getString(R.string.live_viewers), twitchChannel.getCurrentViewers()));

        if(twitchChannel.isOnline()){
            holder.mFeaturedBoxView.setBackgroundColor(ctx.getResources().getColor(R.color.twitch_purple));
            holder.mTitleSubText.setVisibility(View.VISIBLE);
            holder.mTitleSubText.setText(String.format(ctx.getString(R.string.playing), twitchChannel.getMetaGame()));
        }

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
        return twitchChannelList.size();
    }

}
