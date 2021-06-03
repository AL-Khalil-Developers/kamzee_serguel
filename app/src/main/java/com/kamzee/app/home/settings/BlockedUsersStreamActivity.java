package com.kamzee.app.home.settings;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kamzee.app.R;
import com.kamzee.app.adapters.datoo.BlockedUsersAdapter;
import com.kamzee.app.adapters.datoo.BlockedUsersStreamAdapter;
import com.kamzee.app.models.datoo.BlockStreamingModel;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.utils.Tools;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BlockedUsersStreamActivity extends AppCompatActivity {

    Toolbar toolbar;

    User mCurrentUser;

    ArrayList<BlockStreamingModel> mBlockedUsers;
    BlockedUsersStreamAdapter mBlockedUsersAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_users);

        toolbar = findViewById(R.id.toolbar);

        mCurrentUser = (User) ParseUser.getCurrentUser();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.blocked_users_streaming));
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);

        mRecyclerView = findViewById(R.id.rvBlockedUsers);

        mBlockedUsers = new ArrayList<>();
        mBlockedUsersAdapter = new BlockedUsersStreamAdapter(this, mBlockedUsers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(mBlockedUsersAdapter);
        mRecyclerView.setItemViewCacheSize(12);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setBackgroundResource(R.color.white);
        mRecyclerView.setBackgroundColor(Color.WHITE);
        mRecyclerView.setLayoutManager(layoutManager);

        ParseQuery<BlockStreamingModel> blockStreamingModelParseQueryOwner = BlockStreamingModel.getBlockStreamingQuery();
        blockStreamingModelParseQueryOwner.whereEqualTo(BlockStreamingModel.OWNER_USER, mCurrentUser);

        ParseQuery<BlockStreamingModel> blockStreamingModelParseQueryFriend = BlockStreamingModel.getBlockStreamingQuery();
        blockStreamingModelParseQueryFriend.whereEqualTo(BlockStreamingModel.BLOCKER_USER, mCurrentUser);

        ParseQuery<BlockStreamingModel> blockStreamingModelParseQuery = ParseQuery.or(Arrays.asList(blockStreamingModelParseQueryOwner, blockStreamingModelParseQueryFriend));
        blockStreamingModelParseQuery.findInBackground((objects, e) -> {

            if (e == null && objects.size() > 0){

                mBlockedUsers.addAll(objects);
                mBlockedUsersAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}