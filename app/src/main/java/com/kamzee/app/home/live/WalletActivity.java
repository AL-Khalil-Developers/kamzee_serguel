package com.kamzee.app.home.live;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import com.kamzee.app.R;
import com.kamzee.app.app.Config;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.models.datoo.FollowModel;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.models.datoo.WithdrawModel;
import com.kamzee.app.utils.Tools;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Objects;

public class WalletActivity extends AppCompatActivity {


    Toolbar toolbar;

    TextView mTokens, mFollowers, mViewers, mLiveStreaming;
    AppCompatButton mStartLive;

    User mCurrentUser;

    BottomSheetDialog sheetDialog;

    String exchangeSelected = "exchange";
    String withdrawSelected = "withdraw";

    int minTokenToContinue = 0;
    String anyActionSelected = null;
    int diamondsAmountSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        toolbar = findViewById(R.id.toolbar);

        mTokens = findViewById(R.id.tokens);
        mFollowers = findViewById(R.id.stats_followers);
        mViewers = findViewById(R.id.stats_viewers);
        mLiveStreaming = findViewById(R.id.stats_streams);

        mStartLive = findViewById(R.id.streamingList_goLiveButton);

        mCurrentUser = (User) ParseUser.getCurrentUser();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);

        mFollowers.setText(String.valueOf(mCurrentUser.getFollowersCount()));
        if (mCurrentUser.getLiveStreamsViewersUid() != null){
            mViewers.setText(String.valueOf(mCurrentUser.getLiveStreamsViewersUid().size()));
        } else {
            mViewers.setText("0");
        }

        mTokens.setText(String.valueOf(mCurrentUser.getTokens()));

        mLiveStreaming.setText(String.valueOf(mCurrentUser.getLiveSteamsCount()));

        mLiveStreaming.setOnClickListener(v -> QuickHelp.goToActivityWithNoClean(this, LiveActivity.class));

        mStartLive.setOnClickListener(v -> OpenExchange());

        init();

    }

    private void OpenExchange() {

        sheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        sheetDialog.setContentView(R.layout.include_tokens_exchange);
        sheetDialog.setCancelable(true);
        sheetDialog.setCanceledOnTouchOutside(true);
        sheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        TextView tokensTxt = sheetDialog.findViewById(R.id.tokens);
        TextView messageTxt = sheetDialog.findViewById(R.id.message);
        Button actionBtn = sheetDialog.findViewById(R.id.primary_action);

        //EditText textAmount = sheetDialog.findViewById(R.id.amount);
        //TextInputLayout amountLyt = sheetDialog.findViewById(R.id.paypal_email_amount);

        EditText textPaypalEmail = sheetDialog.findViewById(R.id.paypal_email);
        TextInputLayout paypalLyt = sheetDialog.findViewById(R.id.paypal_email_input);

        RadioGroup radioGroup  = sheetDialog.findViewById(R.id.exchange_group);
        AppCompatRadioButton mExchange = sheetDialog.findViewById(R.id.exchange);
        AppCompatRadioButton mWithdraw = sheetDialog.findViewById(R.id.withdraw);

        RadioGroup mExchangeGroup = sheetDialog.findViewById(R.id.radio_exchange);
        AppCompatRadioButton exchange_200 = sheetDialog.findViewById(R.id._200);
        AppCompatRadioButton exchange_500 = sheetDialog.findViewById(R.id._500);
        AppCompatRadioButton exchange_2000 = sheetDialog.findViewById(R.id._2000);
        AppCompatRadioButton exchange_10000 = sheetDialog.findViewById(R.id._10000);
        AppCompatRadioButton exchange_40000_ = sheetDialog.findViewById(R.id._40000_);


        RadioGroup mWithdrawGroup = sheetDialog.findViewById(R.id.radio_withdraw);
        AppCompatRadioButton withdraw_40000 = sheetDialog.findViewById(R.id._40000);
        AppCompatRadioButton withdraw_80000 = sheetDialog.findViewById(R.id._80000);
        AppCompatRadioButton withdraw_160000 = sheetDialog.findViewById(R.id._160000);
        AppCompatRadioButton withdraw_200000 = sheetDialog.findViewById(R.id._200000);
        AppCompatRadioButton withdraw_400000 = sheetDialog.findViewById(R.id._400000);

        assert tokensTxt != null;
        assert messageTxt != null;
        assert actionBtn != null;
        assert radioGroup != null;
        assert mExchange != null;
        assert mWithdraw != null;
        assert paypalLyt != null;
        assert mExchangeGroup != null;
        assert mWithdrawGroup != null;
        assert textPaypalEmail != null;

        assert exchange_200 != null;
        assert exchange_500 != null;
        assert exchange_2000 != null;
        assert exchange_10000 != null;
        assert exchange_40000_ != null;

        assert withdraw_40000 != null;
        assert withdraw_80000 != null;
        assert withdraw_160000 != null;
        assert withdraw_200000 != null;
        assert withdraw_400000 != null;

        tokensTxt.setText(String.valueOf(mCurrentUser.getTokens()));

        if (anyActionSelected == null){
            actionBtn.setEnabled(false);

            paypalLyt.setVisibility(View.GONE);
            mExchangeGroup.setVisibility(View.GONE);
            mWithdrawGroup.setVisibility(View.GONE);

        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (group.getCheckedRadioButtonId() == mExchange.getId()){

                anyActionSelected = exchangeSelected;
                minTokenToContinue = Config.MinTokenExchange;

                messageTxt.setText(getString(R.string.exchange_to_credits));

                actionBtn.setText(getString(R.string.exchange));
                actionBtn.setEnabled(true);

                paypalLyt.setVisibility(View.GONE);

                mExchangeGroup.setVisibility(View.VISIBLE);
                mWithdrawGroup.setVisibility(View.GONE);

            } else if (group.getCheckedRadioButtonId() == mWithdraw.getId()){

                anyActionSelected = withdrawSelected;
                minTokenToContinue = Config.MinTokenWithdraw;

                messageTxt.setText(getString(R.string.withdraw_to_paypal));

                actionBtn.setText(getString(R.string.wallet_withdraw_pay));
                actionBtn.setEnabled(true);

                paypalLyt.setVisibility(View.VISIBLE);

                mExchangeGroup.setVisibility(View.GONE);
                mWithdrawGroup.setVisibility(View.VISIBLE);

            }
        });

        mExchangeGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (group.getCheckedRadioButtonId() == exchange_200.getId()){

                diamondsAmountSelected = 200;

            } else if (group.getCheckedRadioButtonId() == exchange_500.getId()){

                diamondsAmountSelected = 500;

            } else if (group.getCheckedRadioButtonId() == exchange_2000.getId()){

                diamondsAmountSelected = 2000;

            } else if (group.getCheckedRadioButtonId() == exchange_10000.getId()){

                diamondsAmountSelected = 10000;

            } else if (group.getCheckedRadioButtonId() == exchange_40000_.getId()){

                diamondsAmountSelected = 40000;
            }
        });

        mWithdrawGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (group.getCheckedRadioButtonId() == withdraw_40000.getId()){

                diamondsAmountSelected = 40000;

            } else if (group.getCheckedRadioButtonId() == withdraw_80000.getId()){

                diamondsAmountSelected = 80000;

            } else if (group.getCheckedRadioButtonId() == withdraw_160000.getId()){

                diamondsAmountSelected = 160000;

            } else if (group.getCheckedRadioButtonId() == withdraw_200000.getId()){

                diamondsAmountSelected = 200000;

            } else if (group.getCheckedRadioButtonId() == withdraw_400000.getId()){

                diamondsAmountSelected = 400000;
            }
        });

        actionBtn.setOnClickListener(view -> {

            if (anyActionSelected.equals(exchangeSelected) && mCurrentUser.getTokens() >= Config.MinTokenExchange){

                if (diamondsAmountSelected > 0){

                    if (diamondsAmountSelected < Config.MinTokenExchange){

                        QuickHelp.showToast(this, String.format(getString(R.string.wallet_excha_min), Config.MinTokenExchange), true);
                        return;
                    }

                    if (diamondsAmountSelected > mCurrentUser.getTokens()){

                        QuickHelp.showToast(this, String.format(getString(R.string.insuficient_token_error), mCurrentUser.getTokens()) , true);
                        return;
                    }



                    int credits = diamondsAmountSelected * Config.TokenExchangeRate;

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setCancelable(false);
                    alert.setTitle(getString(R.string.exchange_to_credits));
                    alert.setMessage(String.format(getString(R.string.amount_final_exchange), diamondsAmountSelected, credits));
                    alert.setPositiveButton(getString(R.string.ok), (dialog, which) -> exchangeTokens(diamondsAmountSelected, credits)).setNegativeButton(getString(R.string.cancel), null).create();
                    alert.show();


                } else {

                    QuickHelp.showToast(WalletActivity.this, getString(R.string.wallet_amount_error), true);
                }

           } else if (anyActionSelected.equals(withdrawSelected) && mCurrentUser.getTokens() >= Config.MinTokenWithdraw){


                if (diamondsAmountSelected == 0){

                    QuickHelp.showToast(WalletActivity.this,getString(R.string.wallet_amount_error), true);

                } else if (textPaypalEmail.getText().length() == 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(textPaypalEmail.getText().toString()).matches()){

                    textPaypalEmail.setError(getString(R.string.invalid_email));

                } else {

                    if (diamondsAmountSelected < Config.MinTokenWithdraw){

                        QuickHelp.showToast(WalletActivity.this, String.format(getString(R.string.wallet_excha_min), Config.MinTokenWithdraw), true);
                        return;
                    }

                    if (diamondsAmountSelected > mCurrentUser.getTokens()){

                        QuickHelp.showToast(this, String.format(getString(R.string.insuficient_token_error), mCurrentUser.getTokens()) , true);
                        return;
                    }

                    int amount = diamondsAmountSelected;
                    int money = amount / Config.WithdrawExchangeRate;

                    if (amount == 40000){
                        checkWithdraw(amount, money, textPaypalEmail);
                    } else if (amount == 80000){
                        checkWithdraw(amount, money, textPaypalEmail);
                    } else if (amount == 160000){
                        checkWithdraw(amount, money, textPaypalEmail);
                    } else if (amount == 200000){
                        checkWithdraw(amount, money, textPaypalEmail);
                    } else if (amount == 400000){
                        checkWithdraw(amount, money, textPaypalEmail);
                    }
                }

           } else {

               QuickHelp.showToast(this, String.format(getString(R.string.insuficient_token), minTokenToContinue), true);
           }

        });

        mTokens.setText(String.valueOf(mCurrentUser.getTokens()));

        sheetDialog.setOnDismissListener(dialogInterface -> anyActionSelected = null);

        if (sheetDialog != null && !sheetDialog.isShowing()){
            sheetDialog.show();
        }

    }

    public void checkWithdraw(int amount, int money, EditText textPaypalEmail){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);
        alert.setTitle(getString(R.string.exchange_to_money));
        alert.setMessage(String.format(getString(R.string.amount_final_withdraw), amount, money, textPaypalEmail.getText().toString()));
        alert.setPositiveButton(getString(R.string.ok), (dialog, which) -> withdrawTokens(amount, money, textPaypalEmail.getText().toString())).setNegativeButton(getString(R.string.cancel), null).create();
        alert.show();
    }

    public void exchangeTokens(int amount, int credits){

        QuickHelp.showLoading(WalletActivity.this, false);

        mCurrentUser.removeTokens(amount);
        mCurrentUser.addCoin(credits);
        mCurrentUser.saveInBackground(e -> {
            if (e == null){

                WithdrawModel withdrawModel = new WithdrawModel();
                withdrawModel.setAuthor(mCurrentUser);
                withdrawModel.setCredits(credits);
                withdrawModel.setTokens(amount);
                withdrawModel.setType(WithdrawModel.TYPE_EXCHANGE);
                withdrawModel.setCompleted(true);
                withdrawModel.saveInBackground();

                QuickHelp.showToast(WalletActivity.this, getString(R.string.tokens_exchanged), false);

                if (sheetDialog.isShowing()) {
                    sheetDialog.dismiss();
                }

            } else {
                QuickHelp.showToast(WalletActivity.this, getString(R.string.error_ocurred), true);
            }

            QuickHelp.hideLoading(WalletActivity.this);
        });

    }

    public void withdrawTokens(int amount, int credits, String paypalEmail){

        QuickHelp.showLoading(WalletActivity.this, false);

        WithdrawModel withdrawModel = new WithdrawModel();
        withdrawModel.setAuthor(mCurrentUser);
        withdrawModel.setCredits(credits);
        withdrawModel.setTokens(amount);
        withdrawModel.setPayPalEmail(paypalEmail);
        withdrawModel.setCompleted(false);
        withdrawModel.setType(WithdrawModel.TYPE_WITHDRAW);
        withdrawModel.saveInBackground(e -> {
            if (e == null){

                mCurrentUser.removeTokens(amount);
                mCurrentUser.saveInBackground();

                QuickHelp.showToast(WalletActivity.this, getString(R.string.tokens_withdrawed), false);

                if (sheetDialog.isShowing()) {
                    sheetDialog.dismiss();
                }

            } else {
                QuickHelp.showToast(WalletActivity.this, getString(R.string.error_ocurred), true);
            }

            QuickHelp.hideLoading(WalletActivity.this);
        });

    }

    public void init(){

        ParseQuery<FollowModel> followModelParseQuery = FollowModel.getQuery();
        followModelParseQuery.whereEqualTo(FollowModel.TO_AUTHOR, mCurrentUser);
        followModelParseQuery.countInBackground((count, e) -> {

            if (e == null){

                mCurrentUser.setFollowersCount(count);
                mCurrentUser.saveInBackground();

                runOnUiThread(() -> mFollowers.setText(String.valueOf(mCurrentUser.getFollowersCount())));


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTokens.setText(String.valueOf(mCurrentUser.getTokens()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.wallet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.withdraw) {
            QuickHelp.goToActivityWithNoClean(this, WithdrawListActivity.class);

            return true;
        }// If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
}