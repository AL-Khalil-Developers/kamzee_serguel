package com.kamzee.app.home.profile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kamzee.app.R;
import com.kamzee.app.adapters.ImageSliderAdapter;
import com.kamzee.app.app.Application;
import com.kamzee.app.helpers.QuickActions;
import com.kamzee.app.helpers.QuickHelp;
import com.kamzee.app.home.HomeActivity;
import com.kamzee.app.home.payments.PaymentsActivity;
import com.kamzee.app.home.popularity.PopularityActivity;
import com.kamzee.app.home.settings.SettingsActivity;
import com.kamzee.app.home.uploads.UploadsActivity;
import com.kamzee.app.models.SliderItem;
import com.kamzee.app.models.datoo.User;
import com.kamzee.app.modules.circularimageview.CircleImageView;
import com.greysonparrelli.permiso.Permiso;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyProfileFragment extends Fragment {

    public User mCurrentUser;

    private CircleImageView circleImageView;
    private TextView mNameAndAge, mTapToSee;

    private ImageView mPopularityImage;
    private TextView mPopularityText;

    private ImageView mSecondBigImage, mProfileBannerImage, mAddPhotos;
    private TextView mSecondBigText, mSecondSmallText;

    private TextView mCredits, mProfileBannerText;

    private CircleImageView mInvisibleMode;

    private View mCreditsView, mPopularityView, mSecondView, mProfileBannerView;


//    ImageSlider imageSlider;
    FirebaseDatabase firebaseDatabase;
    SliderView mSliderView;
    CardView mSliderCardView;


    public MyProfileFragment() {
        // Required empty public constructor
    }

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle b) {
        super.onViewCreated(view, b);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);

        mSliderView = v.findViewById(R.id.imageSliderView);
        mSliderCardView = v.findViewById(R.id.imageSlider_cardView);


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.child("BannerSlider").exists()){
                    mSliderCardView.setVisibility(View.GONE);
                }else{
                    mSliderCardView.setVisibility(View.VISIBLE);
                    final  List<SliderItem> mSliderItem = new ArrayList<>();
                    FirebaseDatabase.getInstance().getReference().child("BannerSlider").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                mSliderItem.add(new SliderItem(ds.child("imageLink").getValue().toString(),ds.child("webLink").getValue().toString()));
                            }
                            mSliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(),mSliderItem));
//                            mSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//                            mSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//                            mSliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//                            mSliderView.setIndicatorSelectedColor(Color.WHITE);
//                            mSliderView.setIndicatorUnselectedColor(Color.GRAY);
                            mSliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                            mSliderView.startAutoCycle();
                            mSliderView.setIndicatorEnabled(false);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                mSliderCardView.setVisibility(View.GONE);
            }
        });

//        imageSlider = v.findViewById(R.id.mSliderView);
//        final List<SlideModel> remoteFirebase = new ArrayList<>();
//        FirebaseDatabase.getInstance().getReference().child("BannerSlider").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    remoteFirebase.add(new SlideModel(ds.child("imageLink").getValue().toString(),ds.child("title").getValue().toString(), ScaleTypes.CENTER_CROP));
//                }
//                imageSlider.setImageList(remoteFirebase,ScaleTypes.CENTER_CROP);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        circleImageView = v.findViewById(R.id.avatar);
        mNameAndAge = v.findViewById(R.id.myProfileNameAndAge);
        mTapToSee = v.findViewById(R.id.myProfileSubtitle);

        mInvisibleMode = v.findViewById(R.id.invisibleModeIcon);

        mCredits = v.findViewById(R.id.ownProfileElementOneSubtitle);

        mAddPhotos = v.findViewById(R.id.popularity_promoBadgeRight);

        mPopularityImage = v.findViewById(R.id.ownProfileElementThreeImage);
        mPopularityText = v.findViewById(R.id.ownProfileElementThreeSubtitle);

        mSecondBigImage = v.findViewById(R.id.ownProfileElementTwoImage);
        mSecondBigText = v.findViewById(R.id.ownProfileElementTwoTitle);
        mSecondSmallText = v.findViewById(R.id.ownProfileElementTwoSubtitle);

        mProfileBannerImage = v.findViewById(R.id.myProfileBannerImage);
        mProfileBannerText = v.findViewById(R.id.myProfileBannerText);

        mCreditsView = v.findViewById(R.id.ownProfileElementOneClickableArea);
        mPopularityView = v.findViewById(R.id.ownProfileElementThreeClickableArea);
        mSecondView = v.findViewById(R.id.ownProfileElementTwoClickableArea);
        mProfileBannerView = v.findViewById(R.id.myProfileBannerBackground);


        mCurrentUser = User.getUser();



        if (mCurrentUser.getPrivacyAlmostInvisible()){
            mInvisibleMode.setVisibility(View.VISIBLE);
        } else {
            mInvisibleMode.setVisibility(View.GONE);
        }

        mCredits.setText(String.valueOf(mCurrentUser.getCoins()));

        mPopularityImage.setImageResource(QuickHelp.getPopularityLevelIndicator(mCurrentUser));
        mPopularityText.setText(QuickHelp.getPopularityLevel(mCurrentUser));

        if (mCurrentUser.getProfilePhotos().size() < 2){

            mSecondBigImage.setImageResource(R.drawable.ic_badge_add_photos);
            mSecondBigText.setText(getString(R.string.photos_needed));
            mSecondSmallText.setText(getString(R.string.add_photos));

            mSecondView.setOnClickListener(v4 -> QuickHelp.goToActivityWithNoClean(getActivity(), UploadsActivity.class));

        } else if (mCurrentUser.isPremium()){

            mSecondBigImage.setImageResource(R.drawable.ic_badge_feature_premium);
            mSecondBigText.setText(getString(R.string.datoo_premium));
            mSecondSmallText.setText(getString(R.string.activate));

            mSecondView.setOnClickListener(v1 -> QuickHelp.goToActivityWithNoClean(getActivity(), PaymentsActivity.class, PaymentsActivity.DATOO_PAYMENT_TYPE, PaymentsActivity.TYPE_DATOO_PREMIUM));

        } else {

            mSecondBigImage.setImageResource(R.drawable.ic_badge_add_photos);
            mSecondBigText.setText(getString(R.string.get_more_visits));
            mSecondSmallText.setText(getString(R.string.add_photos));

            mSecondView.setOnClickListener(v4 -> QuickHelp.goToActivityWithNoClean(getActivity(), UploadsActivity.class));
        }

        mCreditsView.setOnClickListener(v1 -> QuickHelp.goToActivityWithNoClean(getActivity(), PaymentsActivity.class, PaymentsActivity.DATOO_PAYMENT_TYPE, PaymentsActivity.TYPE_3X_POPULAR));
        mPopularityView.setOnClickListener(v2 -> QuickHelp.goToActivityWithNoClean(getActivity(), PopularityActivity.class));
        mAddPhotos.setOnClickListener(v3 -> QuickHelp.goToActivityWithNoClean(getActivity(), UploadsActivity.class));


        if (mCurrentUser.isVerified()){
            mProfileBannerImage.setImageResource(R.drawable.ic_badge_feature_verification_verified);
            mProfileBannerView.setBackground(Application.getInstance().getResources().getDrawable(R.drawable.notification_background_rounded_with_border_verified));
            mProfileBannerText.setText(getString(R.string.verified));
            mProfileBannerText.setTextColor(Color.WHITE);

        } else {
            mProfileBannerImage.setImageResource(R.drawable.ic_badge_feature_verification_unverified);
            mProfileBannerView.setBackground(Application.getInstance().getResources().getDrawable(R.drawable.notification_background_rounded_with_border_unverified));
            mProfileBannerText.setText(getString(R.string.verify_account));
            mProfileBannerText.setTextColor(Color.WHITE);

            mProfileBannerView.setOnClickListener(v12 -> {

                if (mCurrentUser.getEmail() != null && !mCurrentUser.getEmail().isEmpty()){

                    QuickHelp.showLoading(getActivity(), false);

                    mCurrentUser.setEmail(mCurrentUser.getEmail());
                    mCurrentUser.saveInBackground(e -> {

                        if (e == null){

                            QuickHelp.hideLoading(getActivity());
                            QuickHelp.showToast(Objects.requireNonNull(getActivity()), getString(R.string.email_verification_sent), false);
                        } else {
                            QuickHelp.hideLoading(getActivity());
                            QuickHelp.showToast(Objects.requireNonNull(getActivity()), getString(R.string.error_ocurred), true);
                        }
                    });

                } else {

                    QuickHelp.showToast(Objects.requireNonNull(getActivity()), getString(R.string.email_not_exist), true);
                }
            });

        }

        Permiso.getInstance().setActivity(Objects.requireNonNull(getActivity()));

        if (getActivity() != null) {

            ((HomeActivity)getActivity()).initializeToolBar(R.drawable.ic_navigation_bar_settings, R.drawable.ic_navigation_bar_edit_profile, HomeActivity.VIEW_TYPE_MY_PROFILE);

            mNameAndAge.setOnClickListener(v1 -> QuickActions.showProfile(getActivity(), mCurrentUser, true));
            mTapToSee.setOnClickListener(v1 -> QuickActions.showProfile(getActivity(), mCurrentUser, true));
            circleImageView.setOnClickListener(v1 -> QuickActions.showProfile(getActivity(), mCurrentUser, true));
        }

        setHasOptionsMenu(true);


        return v;

    }

    public void getIconLeft(Activity activity){

        QuickHelp.goToActivityWithNoClean(activity, SettingsActivity.class);

    }

    public void getIconRight(Activity activity){

        QuickHelp.goToActivityWithNoClean(activity, EditProfileActivity.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Objects.requireNonNull(getActivity()).onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Permiso.getInstance().setActivity(Objects.requireNonNull(getActivity()));

        if (mCurrentUser != null){

            mCurrentUser.fetchInBackground();

            QuickHelp.getAvatars(mCurrentUser, circleImageView);

            mCredits.setText(String.valueOf(mCurrentUser.getCoins()));

            if (mCurrentUser.getBirthDate() != null){

                mNameAndAge.setText(String.format(" %s, %s", mCurrentUser.getColFullName(), QuickHelp.getAgeFromDate(mCurrentUser.getBirthDate())));
            }  else mNameAndAge.setText(mCurrentUser.getColFullName());
        }
    }
}