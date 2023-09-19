package com.fitstir.fitstirapp.ui.runtracker.utilites;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class Methods  {

    public void chartDesignMain(LineChart chart, ArrayList<Entry> list, ArrayList<Entry> list2, ArrayList<Entry> list3){

        Description description = new Description();
        description.setText("Endurance Progress");
        description.setTextSize(14f);
        chart.setDescription(description);

        Legend legend = chart.getLegend();
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);


        LineDataSet minSet = new LineDataSet(list, "Run Times");
        minSet.setColor(Color.GREEN);
        minSet.setLineWidth(4f);
        minSet.setDrawCircles(true);
        minSet.setCircleColor(Color.BLACK);

        LineDataSet distSet = new LineDataSet(list2,"Run Distance");
        distSet.setColor(Color.RED);
        distSet.setLineWidth(4f);
        distSet.setDrawCircles(true);
        distSet.setCircleColor(Color.BLACK);

        LineDataSet calSet = new LineDataSet(list3,"Calories Burned");
        calSet.setColor(Color.BLUE);
        calSet.setLineWidth(4f);
        calSet.setDrawCircles(true);
        calSet.setCircleColor(Color.BLACK);


        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        chart.animateX(1000, Easing.EaseInOutCubic);


        LineData lineData = new LineData(minSet,distSet, calSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}
