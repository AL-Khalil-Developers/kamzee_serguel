package com.kamzee.app.modules.charting.interfaces.dataprovider;

import com.kamzee.app.modules.charting.components.YAxis;
import com.kamzee.app.modules.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
