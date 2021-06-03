package com.kamzee.app.modules.charting.interfaces.dataprovider;

import com.kamzee.app.modules.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
