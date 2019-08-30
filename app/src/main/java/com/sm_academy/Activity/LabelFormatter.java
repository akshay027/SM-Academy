/*
package com.sm_academy.Activity;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class LabelFormatter implements IAxisValueFormatter {
    private final String[] mLabels;

    public LabelFormatter(String[] labels) {
        mLabels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int days = (int) value;
        if (days > mLabels.length) {
            return mLabels[0];
        } else {
            return mLabels[days];
        }
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}*/
