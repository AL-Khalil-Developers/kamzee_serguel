package com.kamzee.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamzee.app.R;
import com.kamzee.app.adapters.ImageSliderAdapter.SliderViewHolder;
import com.kamzee.app.models.SliderDataHolder;


import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends com.smarteist.autoimageslider.SliderViewAdapter<SliderViewHolder> {

    Context context;
    List<SliderDataHolder> mSliderItems = new ArrayList<>();

    public ImageSliderAdapter(Context context, List<SliderDataHolder> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout,parent,false);
        return new SliderViewHolder(view);
    }



    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.titleTxt.setText(mSliderItems.get(position).getTitle());
        Glide.with(viewHolder.itemView).load(mSliderItems.get(position).getImageLink()).centerCrop().into(viewHolder.sliderImageView);
    }

    @Override
    public int getCount() {
        return mSliderItems.toArray().length;
    }

    public class SliderViewHolder extends ViewHolder {
        ImageView sliderImageView;
        TextView titleTxt;
        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderImageView = itemView.findViewById(R.id.sliderImageView);
            titleTxt = itemView.findViewById(R.id.sliderTitleTxt);
        }
    }
}
