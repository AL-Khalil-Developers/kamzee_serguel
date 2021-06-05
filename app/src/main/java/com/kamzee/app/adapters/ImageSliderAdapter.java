package com.kamzee.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamzee.app.R;
import com.kamzee.app.models.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.kamzee.app.adapters.ImageSliderAdapter.*;

public class ImageSliderAdapter extends SliderViewAdapter<SliderAdapterVH> {

    Context context;
    List<SliderItem> mSliderItems = new ArrayList<>();

    public ImageSliderAdapter(Context context, List<SliderItem> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        SliderItem sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView).load(sliderItem.getImageLink()).into(viewHolder.bannerImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = sliderItem.getWebLink().toString();
                Intent openWebLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(openWebLinkIntent);
            }
        });

    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public static class SliderAdapterVH extends ViewHolder {
        ImageView bannerImage;
        View itemView;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.imageView_slider);
            this.itemView = itemView;
        }
    }
}
