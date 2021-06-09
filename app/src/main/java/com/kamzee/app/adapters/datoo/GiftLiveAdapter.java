package com.kamzee.app.adapters.datoo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kamzee.app.R;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.home.live.LiveStreamingActivity;
import com.kamzee.app.models.datoo.GiftModel;

import java.util.List;

public class GiftLiveAdapter extends RecyclerView.Adapter<GiftLiveAdapter.ViewHolder> {
    private List<GiftModel> mGifts;
    private Activity mActivity;


    public GiftLiveAdapter(Activity activity, List<GiftModel> giftModelList) {
        mGifts = giftModelList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;

        view = inflater.inflate(R.layout.item_live_gift_large, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GiftModel gift = mGifts.get(position);

        if (gift.getGiftFile().getUrl().endsWith(".json")){

            viewHolder.mGiftImageGIF.setVisibility(View.GONE);
            viewHolder.mGiftImageLottie.setVisibility(View.VISIBLE);

            viewHolder.mGiftImageLottie.setAnimationFromUrl(gift.getGiftFile().getUrl());
            viewHolder.mGiftImageLottie.setSpeed(0.8f);

        } else if (gift.getGiftFile().getUrl().endsWith(".gif")){

            viewHolder.mGiftImageGIF.setVisibility(View.VISIBLE);
            viewHolder.mGiftImageLottie.setVisibility(View.GONE);

            QuickHelp.showGifFile(mActivity, viewHolder.mGiftImageGIF, gift.getGiftFile().getUrl());

        }

        viewHolder.mGiftCredits.setText(String.valueOf(gift.getCoins()));
        viewHolder.mLayout.setOnClickListener(v -> ((LiveStreamingActivity) mActivity).sendLiveGift(gift));

    }

    @Override
    public int getItemCount() {
        return mGifts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout mLayout;
        LottieAnimationView mGiftImageLottie;
        ImageView mGiftImageGIF;
        TextView mGiftCredits;

        ViewHolder(View v) {
            super(v);

            mLayout = v.findViewById(R.id.root_view);
            mGiftImageLottie = v.findViewById(R.id.itemLiveGift_lottieImage);
            mGiftImageGIF = v.findViewById(R.id.itemLiveGift_GifImage);
            mGiftCredits = v.findViewById(R.id.itemLiveGift_priceText);
        }
    }

}