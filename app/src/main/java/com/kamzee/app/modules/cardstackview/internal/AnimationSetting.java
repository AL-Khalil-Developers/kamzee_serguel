package com.kamzee.app.modules.cardstackview.internal;

import android.view.animation.Interpolator;

import com.kamzee.app.modules.cardstackview.Direction;


public interface AnimationSetting {
    Direction getDirection();
    int getDuration();
    Interpolator getInterpolator();
}
