package com.kamzee.app.home.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kamzee.app.R;
import com.kamzee.app.app.Config;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.home.settings.accountPreferences.AccountPreferencesActivity;
import com.kamzee.app.home.settings.about.AboutActivity;
import com.kamzee.app.home.settings.account.AccountActivity;
import com.kamzee.app.home.settings.basicInfo.BasicInfoActivity;
import com.kamzee.app.models.datoo.BlockStreamingModel;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.utils.Tools;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    User mCurrentUser;

    LinearLayout mBasicInfo, mAccount, mAccountPreference, mHelpCenter, mAbout, mBlockedUsers, mBlockedUsersStreaming;

    TextView mAccountEmail, mBlockedUsersText, mBlockedUsersStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.toolbar);

        mAccountEmail = findViewById(R.id.account_email);

        mBasicInfo = findViewById(R.id.basic_info);
        mAccount = findViewById(R.id.account);
        mAccountPreference = findViewById(R.id.account_preference);
        mHelpCenter = findViewById(R.id.help_center);
        mAbout = findViewById(R.id.about);
        mBlockedUsers = findViewById(R.id.blocked_users);
        mBlockedUsersStreaming = findViewById(R.id.blocked_users_streaming);
        mBlockedUsersText = findViewById(R.id.blocked_users_text);
        mBlockedUsersStream = findViewById(R.id.blocked_users_stream);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.Settings));
        getSupportActionBar().setElevation(4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);

        mCurrentUser = (User) User.getCurrentUser();

        if (mCurrentUser != null){

            if (!mCurrentUser.getEmail().isEmpty()){

                mAccountEmail.setText(mCurrentUser.getEmail());

            } else mAccountEmail.setVisibility(View.GONE);


            if (mCurrentUser.getBlockedUsers() != null && mCurrentUser.getBlockedUsers().size() > 0){

                mBlockedUsers.setEnabled(true);
                mBlockedUsersText.setTextColor(getResources().getColor(R.color.black));

            } else {

                mBlockedUsers.setEnabled(false);
                mBlockedUsersText.setTextColor(getResources().getColor(R.color.gray_24));
            }

            ParseQuery<BlockStreamingModel> blockStreamingModelParseQueryOwner = BlockStreamingModel.getBlockStreamingQuery();
            blockStreamingModelParseQueryOwner.whereEqualTo(BlockStreamingModel.OWNER_USER, mCurrentUser);

            ParseQuery<BlockStreamingModel> blockStreamingModelParseQueryFriend = BlockStreamingModel.getBlockStreamingQuery();
            blockStreamingModelParseQueryFriend.whereEqualTo(BlockStreamingModel.BLOCKER_USER, mCurrentUser);

            ParseQuery<BlockStreamingModel> blockStreamingModelParseQuery = ParseQuery.or(Arrays.asList(blockStreamingModelParseQueryOwner, blockStreamingModelParseQueryFriend));
            blockStreamingModelParseQuery.findInBackground((objects, e) -> {

                if (e == null && objects.size() > 0){

                    mBlockedUsersStreaming.setEnabled(true);
                    mBlockedUsersStream.setTextColor(getResources().getColor(R.color.black));

                } else {

                    mBlockedUsersStreaming.setEnabled(false);
                    mBlockedUsersStream.setTextColor(getResources().getColor(R.color.gray_24));
                }
            });

        }

        mBasicInfo.setOnClickListener(v -> goToBasicInfo());
        mAccount.setOnClickListener(v -> goToAccount());
        mAccountPreference.setOnClickListener(v -> goToAccountPreference());
        mHelpCenter.setOnClickListener(v -> goToHelpCenter());
        mAbout.setOnClickListener(v -> goToAbout());
        mBlockedUsers.setOnClickListener(v -> goToBlockedUsers());
        mBlockedUsersStreaming.setOnClickListener(v -> goToBlockedUsersStreaming());

    }


    public void goToBasicInfo(){

        QuickHelp.goToActivityWithNoClean(this, BasicInfoActivity.class);
    }

    public void goToAccount(){

        QuickHelp.goToActivityWithNoClean(this, AccountActivity.class);

    }

    public void goToAccountPreference(){

        QuickHelp.goToActivityWithNoClean(this, AccountPreferencesActivity.class);
    }

    public void goToHelpCenter(){

        QuickHelp.goToActivityWithNoClean(this, WebUrlsActivity.class, WebUrlsActivity.WEB_URL_TYPE, Config.HELP_CENTER);
    }

    public void goToAbout(){

        QuickHelp.goToActivityWithNoClean(this, AboutActivity.class);

    }

    public void goToBlockedUsers(){

        QuickHelp.goToActivityWithNoClean(this, BlockedUsersActivity.class);
    }

    public void goToBlockedUsersStreaming(){

        QuickHelp.goToActivityWithNoClean(this, BlockedUsersStreamActivity.class);
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