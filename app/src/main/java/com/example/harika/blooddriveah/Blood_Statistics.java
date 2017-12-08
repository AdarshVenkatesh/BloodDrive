package com.example.harika.blooddriveah;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class Blood_Statistics extends AppCompatActivity {


    private float[] yData={20f,28f,36f,16f};
    private String[] xData={"Type B","Type A","Type O","Type AB"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__statistics);
        pieChart=(PieChart)findViewById(R.id.piechart);
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Blood types of 1000 people");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("Statistics",e.toString());
                Log.d("Statistics",h.toString());

                int pos1=e.toString().indexOf("y:");
                String types=e.toString().substring(pos1+3);

                for(int i=0;i<yData.length;i++)
                {
                    if(yData[i]==Float.parseFloat(types)) {
                        pos1 = i;
                        break;
                    }
                }

                String bloodType=xData[pos1];
                Toast.makeText(Blood_Statistics.this, "People of "+bloodType+" is of "+types+"% of total population", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    private void addDataSet()
    {
        ArrayList<PieEntry> yEntrys=new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();


        for(int i=0;i<yData.length;i++)
        {
            yEntrys.add(new PieEntry(yData[i],i));
        }

        for(int i=0;i<xData.length;i++)
        {
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Blood groups");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueTextColor(Color.WHITE);

        ArrayList<Integer> colors=new ArrayList<>();

        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();



    }
}
