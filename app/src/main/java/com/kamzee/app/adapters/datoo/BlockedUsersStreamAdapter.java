package com.kamzee.app.adapters.datoo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kamzee.app.R;
import com.kamzee.app.helpers.QuickActions;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.models.datoo.BlockStreamingModel;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.modules.circularimageview.CircleImageView;
import com.parse.DeleteCallback;
import com.parse.ParseException;

import java.util.List;


public class BlockedUsersStreamAdapter extends RecyclerView.Adapter<BlockedUsersStreamAdapter.ViewHolder> {
    private List<BlockStreamingModel> mBlockStreamingModels;
    private Activity mActivity;


    public BlockedUsersStreamAdapter(Activity activity, List<BlockStreamingModel> blockStreamingModels) {
        mBlockStreamingModels = blockStreamingModels;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;

        view = inflater.inflate(R.layout.list_item_blocked_stream, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BlockStreamingModel blockStreamingModel = mBlockStreamingModels.get(position);

        QuickHelp.getAvatars(blockStreamingModel.getBlockedUser(), viewHolder.mImage);
        viewHolder.mFullName.setText(blockStreamingModel.getBlockedUser().getColFullName());

        viewHolder.mLayout.setOnClickListener(v -> QuickActions.showProfile(mActivity, blockStreamingModel.getBlockedUser(), false));

        viewHolder.mLayout.setOnClickListener(v -> {

            QuickHelp.showLoading(mActivity, false);
            blockStreamingModel.deleteInBackground(e -> {
                if (e == null){
                    QuickHelp.hideLoading(mActivity);
                    mActivity.runOnUiThread(() -> {
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    });
                } else {
                    QuickHelp.hideLoading(mActivity);
                    QuickHelp.showToast(mActivity, mActivity.getString(R.string.error_ocurred), true);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return mBlockStreamingModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mLayout;
        TextView mFullName, mUnBlock;
        CircleImageView mImage;

        ViewHolder(View v) {
            super(v);

            mLayout = v.findViewById(R.id.blocked_users);
            mImage = v.findViewById(R.id.image);
            mFullName = v.findViewById(R.id.name);
            mUnBlock = v.findViewById(R.id.unblock);
        }
    }

}