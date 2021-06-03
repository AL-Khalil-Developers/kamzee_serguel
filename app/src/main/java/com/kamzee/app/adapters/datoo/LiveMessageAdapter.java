package com.kamzee.app.adapters.datoo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kamzee.app.R;
import com.kamzee.app.helpers.QuickActions;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.home.live.LiveStreamingActivity;
import com.kamzee.app.models.datoo.BlockStreamingModel;
import com.kamzee.app.models.datoo.LiveMessageModel;
import com.kamzee.app.models.datoo.LiveStreamModel;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.modules.circularimageview.CircleImageView;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class LiveMessageAdapter extends RecyclerView.Adapter<LiveMessageAdapter.ViewHolder> {

    private static final int TYPE_COMMENT = 0;
    private static final int TYPE_FOLLOW = 1;
    private static final int TYPE_GIFT = 2;

    private List<LiveMessageModel> mLiveMessage;
    private Activity mActivity;
    private User mUser;

    public LiveMessageAdapter(Activity activity, List<LiveMessageModel> liveMessageModels, User user) {
        mLiveMessage = liveMessageModels;
        mActivity = activity;
        mUser = user;
    }

    @Override
    public int getItemViewType(int position) {

        switch (mLiveMessage.get(position).getMessageType()) {
            case LiveMessageModel.MESSAGE_TYPE_COMMENT:

                return TYPE_COMMENT;

            case LiveMessageModel.MESSAGE_TYPE_FOLLOW:

                return TYPE_FOLLOW;

            default:
                return TYPE_GIFT;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_COMMENT:

                View TYPE_COMMENT = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stream_message_regular, parent, false);
                return new ViewHolder(TYPE_COMMENT);

            case TYPE_FOLLOW:

                View TYPE_FOLLOW = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stream_system_message_with_avatar, parent, false);
                return new ViewHolder(TYPE_FOLLOW);

            case TYPE_GIFT:

                View TYPE_GIFT = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stream_gift_message, parent, false);
                return new ViewHolder(TYPE_GIFT);

        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        LiveMessageModel liveMessage = mLiveMessage.get(position);

        QuickHelp.getAvatars(liveMessage.getSenderAuthor(), viewHolder.mUserPhoto);

        if (liveMessage.getLiveStream().getBlockers().size() > 0 && liveMessage.getLiveStream().getBlockers().contains(liveMessage.getSenderAuthor())){
            viewHolder.mUserPhoto.setBorderWidth(6);
            viewHolder.mUserPhoto.setBorderColor(Color.RED);
        } else {
            viewHolder.mUserPhoto.setBorderWidth(0);
        }

        switch (liveMessage.getMessageType()) {
            case LiveMessageModel.MESSAGE_TYPE_COMMENT:

                viewHolder.mFirstName.setText(liveMessage.getSenderAuthor().getColFirstName());
                viewHolder.mMessage.setText(liveMessage.getMessage());

                break;
            case LiveMessageModel.MESSAGE_TYPE_FOLLOW:

                if (liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())) {
                    viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_you_followed_user), liveMessage.getLiveStream().getAuthor().getColFirstName()));

                } else {
                    viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_user_followed_user), liveMessage.getSenderAuthor().getColFirstName(), liveMessage.getLiveStream().getAuthor().getColFirstName()));
                }

                break;

            case LiveMessageModel.MESSAGE_TYPE_BAN:

                if (liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())) {
                    viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_you_banned_user), liveMessage.getBannedUser().getColFirstName()));

                } else {
                    viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_user_banned_user), liveMessage.getSenderAuthor().getColFirstName(), liveMessage.getBannedUser().getColFirstName()));
                }

                break;

            case LiveMessageModel.MESSAGE_TYPE_GIFT:

                if (liveMessage.getLiveGift().getGiftFile() != null){

                    liveMessage.getLiveGift().getGiftFile().getFileInBackground((file, e) -> {
                        if (e == null){

                            if (!liveMessage.getLiveGift().getGiftFile().getUrl().isEmpty() && liveMessage.getLiveGift().getGiftFile().getUrl().endsWith(".json")){

                                viewHolder.mGiftImageGIF.setVisibility(View.GONE);
                                viewHolder.mGiftImgageLottie.setVisibility(View.VISIBLE);

                                viewHolder.mGiftImgageLottie.setAnimationFromUrl(liveMessage.getLiveGift().getGiftFile().getUrl());
                                viewHolder.mGiftImgageLottie.setSpeed(0.8f);

                            } else if (!liveMessage.getLiveGift().getGiftFile().getUrl().isEmpty() && liveMessage.getLiveGift().getGiftFile().getUrl().endsWith(".gif")){

                                viewHolder.mGiftImageGIF.setVisibility(View.VISIBLE);
                                viewHolder.mGiftImgageLottie.setVisibility(View.GONE);

                                QuickHelp.showGifFile(mActivity, viewHolder.mGiftImageGIF, liveMessage.getLiveGift().getGiftFile().getUrl());
                            }
                        }
                    });

                }

                if (liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())) {
                    viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_you_sent_gift_user), liveMessage.getLiveStream().getAuthor().getColFirstName()));

                } else {

                    if (liveMessage.getLiveStream().getAuthor() != null){
                        viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_user_sent_gift_user), liveMessage.getSenderAuthor().getColFirstName(), liveMessage.getLiveStream().getAuthor().getColFirstName()));
                    } else {
                        viewHolder.mSystemMessage.setText(String.format(mActivity.getString(R.string.live_user_sent_gift), liveMessage.getSenderAuthor().getColFirstName()));
                    }
                }
                break;
        }

        viewHolder.mUserPhoto.setOnClickListener(v -> {

            if (liveMessage.getSenderAuthorId().equals(mUser.getObjectId())){
                return;
            }

            if (liveMessage.getLiveStream().getBlockers().contains(mUser)){

                blockUser(mActivity, liveMessage, viewHolder.mUserPhoto, mUser);
            } else if (liveMessage.getLiveStream().getBlockers().size() > 0 && liveMessage.getLiveStream().getBlockers().contains(liveMessage.getSenderAuthor())){
                removeBlocker(mActivity, liveMessage, viewHolder.mUserPhoto);
            } else {
                setBlocker(mActivity, liveMessage, viewHolder.mUserPhoto);
            }

        });

    }

    public void setBlocker(Activity activity, LiveMessageModel liveMessage, CircleImageView avatar){

        final CharSequence[] items = { activity.getString(R.string.add_as_blocker), activity.getString(R.string.live_view_profile),  activity.getString(R.string.cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //builder.setTitle("");
        builder.setItems(items, (dialog, item) -> {

            if (items[item].equals(activity.getString(R.string.add_as_blocker))) {

                liveMessage.getLiveStream().addBlocker(liveMessage.getSenderAuthor());
                liveMessage.getLiveStream().saveInBackground();
                QuickHelp.showToast(mActivity, mActivity.getString(R.string.user_add_blocker), false);

                avatar.setBorderWidth(6);
                avatar.setBorderColor(Color.RED);

            } else if (items[item].equals(activity.getString(R.string.live_view_profile))){

                if (!liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())){
                    QuickActions.showProfile(mActivity, liveMessage.getSenderAuthor(), false);
                }

            }  else if (items[item].equals(activity.getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void removeBlocker(Activity activity, LiveMessageModel liveMessage, CircleImageView avatar){

        final CharSequence[] items = { activity.getString(R.string.remove_as_blocker), activity.getString(R.string.live_view_profile), activity.getString(R.string.cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //builder.setTitle("");
        builder.setItems(items, (dialog, item) -> {

            if (items[item].equals(activity.getString(R.string.remove_as_blocker))) {

                liveMessage.getLiveStream().removeBlocker(liveMessage.getSenderAuthor());
                liveMessage.getLiveStream().saveInBackground();
                QuickHelp.showToast(mActivity, mActivity.getString(R.string.user_removed_blocker), false);

                avatar.setBorderWidth(0);

            }  else if (items[item].equals(activity.getString(R.string.live_view_profile))){

                if (!liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())){
                    QuickActions.showProfile(mActivity, liveMessage.getSenderAuthor(), false);
                }

            } else if (items[item].equals(activity.getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void blockUser(Activity activity, LiveMessageModel liveMessage, CircleImageView avatar, User user){

        final CharSequence[] items = { activity.getString(R.string.block_user), activity.getString(R.string.live_view_profile), activity.getString(R.string.cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //builder.setTitle("");
        builder.setItems(items, (dialog, item) -> {

            if (items[item].equals(activity.getString(R.string.block_user))) {

                QuickHelp.showLoading(activity, false);

                BlockStreamingModel blockStreamingModel = new BlockStreamingModel();
                blockStreamingModel.setAuthor(user);
                blockStreamingModel.setBlockedUser(liveMessage.getSenderAuthor());
                blockStreamingModel.setOwner(liveMessage.getLiveStream().getAuthor());
                blockStreamingModel.setLiveStreaming(liveMessage.getLiveStream());
                blockStreamingModel.saveInBackground(e -> {
                    if (e == null){
                        QuickHelp.hideLoading(activity);
                        QuickHelp.showToast(activity, activity.getString(R.string.user_banned), true);
                        ((LiveStreamingActivity) activity).banUser(liveMessage.getSenderAuthor());
                    } else {
                        QuickHelp.hideLoading(activity);
                        QuickHelp.showToast(activity, activity.getString(R.string.error_ocurred), true);
                    }
                });

            }  else if (items[item].equals(activity.getString(R.string.live_view_profile))){

                if (!liveMessage.getSenderAuthorId().equals(User.getUser().getObjectId())){
                    QuickActions.showProfile(mActivity, liveMessage.getSenderAuthor(), false);
                }

            } else if (items[item].equals(activity.getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public int getItemCount() {
        return mLiveMessage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mUserPhoto;
        TextView mFirstName, mMessage, mSystemMessage;
        LottieAnimationView mGiftImgageLottie;
        ImageView mGiftImageGIF;

        ViewHolder(View v) {
            super(v);

            //All
            mUserPhoto = v.findViewById(R.id.userImageView);

            // Comments
            mFirstName = v.findViewById(R.id.displayName);
            mMessage = v.findViewById(R.id.displayMessage);

            // Follow and Gift
            mSystemMessage = v.findViewById(R.id.systemMessage);

            // Gift
            mGiftImageGIF = v.findViewById(R.id.giftImageView);
            mGiftImgageLottie = v.findViewById(R.id.giftLottieView);
        }

    }
}