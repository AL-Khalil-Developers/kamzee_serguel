package com.kamzee.app.modules.cardstackview.internal;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import com.kamzee.app.modules.cardstackview.Direction;
import com.kamzee.app.modules.cardstackview.RewindAnimationSetting;
import com.kamzee.app.modules.cardstackview.StackFrom;
import com.kamzee.app.modules.cardstackview.SwipeAnimationSetting;
import com.kamzee.app.modules.cardstackview.SwipeableMethod;

import java.util.List;

public class CardStackSetting {
    public StackFrom stackFrom = StackFrom.None;
    public int visibleCount = 3;
    public float translationInterval = 8.0f;
    public float scaleInterval = 0.95f; // 0.0f - 1.0f
    public float swipeThreshold = 0.3f; // 0.0f - 1.0f
    public float maxDegree = 20.0f;
    public List<Direction> directions = Direction.HORIZONTAL;
    public boolean canScrollHorizontal = true;
    public boolean canScrollVertical = true;
    public SwipeableMethod swipeableMethod = SwipeableMethod.AutomaticAndManual;
    public SwipeAnimationSetting swipeAnimationSetting = new SwipeAnimationSetting.Builder().build();
    public RewindAnimationSetting rewindAnimationSetting = new RewindAnimationSetting.Builder().build();
    public Interpolator overlayInterpolator = new LinearInterpolator();
}
