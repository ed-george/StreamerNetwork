package uk.co.edgeorgedev.streamernetwork.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.pkrss.Article;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import uk.co.edgeorgedev.streamernetwork.R;
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
        public ImageView mTimeImage;
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
            mCommentImage.setImageResource(R.drawable.star);
            mTimeImage = (ImageView) v.findViewById(R.id.time_image);
            mTimeImage.setImageResource(R.drawable.mic);
            mFeaturedView = (TextView) v.findViewById(R.id.featured_layout);
            mTitleSubText = (TextView) v.findViewById(R.id.info_sub_text);
            mFeaturedBoxView = v.findViewById(R.id.featured_box_layout);

            mTimeImage.setVisibility(View.GONE);
            mCreatedDate.setVisibility(View.GONE);
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
        final TwitchChannel twitchChannel = twitchChannelList.get(position);

        holder.mFeaturedView.setText(twitchChannel.getStatus());
        holder.mTitleText.setText(twitchChannel.getName());
        Picasso.with(ctx).load(twitchChannel.getImage().getSize600()).error(R.drawable.feed_default).into(holder.mMainImage);

        holder.mMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitchChannel.getLink())));
            }
        });

        holder.mCommentText.setText(MessageFormat.format(ctx.getString(R.string.follower_count),twitchChannel.getFollowersCount()));

        holder.mCreatedDate.setText(MessageFormat.format(ctx.getString(R.string.live_viewers), twitchChannel.getCurrentViewers()));

        if(twitchChannel.isLive()) {

            holder.mTimeImage.setVisibility(View.VISIBLE);
            holder.mCreatedDate.setVisibility(View.VISIBLE);

            holder.mFeaturedBoxView.setBackgroundColor(ctx.getResources().getColor(R.color.twitch_purple));
            if(!twitchChannel.getMetaGame().isEmpty()) {
                holder.mTitleSubText.setVisibility(View.VISIBLE);
                holder.mTitleSubText.setText(String.format(ctx.getString(R.string.playing), twitchChannel.getMetaGame()));
            }

            //SHOW FIRE IF VIEWERS OVER 100? or 10% of followers?
            if (twitchChannel.getCurrentViewers() != 0
                    && twitchChannel.getCurrentViewers() >= Math.round(0.1 * twitchChannel.getFollowersCount())){
                holder.mTimeImage.setImageResource(R.drawable.trending);
            }
        }else{
            holder.mTimeImage.setVisibility(View.INVISIBLE);
            holder.mCreatedDate.setVisibility(View.INVISIBLE);
            holder.mTitleSubText.setVisibility(View.GONE);
            holder.mFeaturedBoxView.setBackgroundColor(ctx.getResources().getColor(R.color.sn_green));
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
