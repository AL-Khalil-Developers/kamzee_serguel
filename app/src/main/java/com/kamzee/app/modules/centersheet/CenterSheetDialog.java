package com.kamzee.app.modules.centersheet;
/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.kamzee.app.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CenterSheetDialog extends AppCompatDialog {

    private CenterSheetBehavior<FrameLayout> centerSheetBehavior;

    public CenterSheetDialog(@NonNull Context context) {
        super(context, getThemeResId(context, 0));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public CenterSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected CenterSheetDialog(@NonNull Context context, boolean cancelable,
                                OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInTopSheet(layoutResId, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInTopSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInTopSheet(0, view, params));
    }

    private View wrapInTopSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        final CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(getContext(),
                R.layout.center_sheet_dialog, null);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout topSheet = coordinator.findViewById(R.id.design_center_sheet);
        centerSheetBehavior = CenterSheetBehavior.from(topSheet);
        centerSheetBehavior.setCenterSheetCallback(mCenterSheetCallback);
        if (params == null) {
            topSheet.addView(view);
        } else {
            topSheet.addView(view, params);
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        if (shouldWindowCloseOnTouchOutside()) {
            coordinator.findViewById(R.id.center_sheet_touch_outside).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isShowing()) {
                                cancel();
                            }
                        }
                    });
        }
        return coordinator;
    }

    private boolean shouldWindowCloseOnTouchOutside() {
        if (true) {
            return true;
        }

        if (Build.VERSION.SDK_INT < 11) {
            return true;
        }
        TypedValue value = new TypedValue();
        //noinspection SimplifiableIfStatement
        if (getContext().getTheme()
                .resolveAttribute(android.R.attr.windowCloseOnTouchOutside, value, true)) {
            return value.data != 0;
        }
        return false;
    }

    @Override
    public void show() {
        super.show();
        centerSheetBehavior.setState(CenterSheetBehavior.STATE_EXPANDED);
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId == 0) {
            // If the provided theme is 0, then retrieve the dialogTheme from our theme
            TypedValue outValue = new TypedValue();
            if (context.getTheme().resolveAttribute(
                    R.attr.bottomSheetDialogTheme, outValue, true)) {
                themeId = outValue.resourceId;
            } else {
                // bottomSheetDialogTheme is not provided; we default to our light theme
                themeId = R.style.Theme_Design_CenterSheetDialog;
            }
        }
        return themeId;
    }

    private CenterSheetBehavior.CenterSheetCallback mCenterSheetCallback
            = new CenterSheetBehavior.CenterSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View topSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == CenterSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View topSheet, float slideOffset) {
        }
    };

    @NonNull
    public CenterSheetBehavior<FrameLayout> getBehavior() {
        return centerSheetBehavior;
    }


}
