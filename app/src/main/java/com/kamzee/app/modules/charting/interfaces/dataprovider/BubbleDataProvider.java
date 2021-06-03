package com.kamzee.app.modules.charting.interfaces.dataprovider;

import com.kamzee.app.modules.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
