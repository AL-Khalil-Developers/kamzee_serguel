package com.kamzee.app.modules.charting.interfaces.dataprovider;

import com.kamzee.app.modules.charting.components.YAxis.AxisDependency;
import com.kamzee.app.modules.charting.data.BarLineScatterCandleBubbleData;
import com.kamzee.app.modules.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
