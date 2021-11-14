package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AttendantAnalysis extends AppCompatActivity {

    private LineChart lineChart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private static final String URL_PRODUCTS = "http://www.json-generator.com/api/json/get/ceASwIGvaW?indent=2";
    private ArrayList<String> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendant_analysis);
        lineChart = findViewById(R.id.lineChart);
        loadAttendChartData();

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setEnabled(false);
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMinimum(0f);

        ArrayList<Entry> ydata = new ArrayList<>();

//        for(int i = 0; i < datalist.size(); i++)
//        {
//            float value = datalist.indexOf(i);
//            Entry entry = new Entry(i, value);
//            ydata.add(entry);
//        }
        ydata.add(new Entry(0,6f));
        ydata.add(new Entry(1,6f));
        ydata.add(new Entry(2,6f));
        ydata.add(new Entry(3,6f));
        ydata.add(new Entry(4,50f));
        ydata.add(new Entry(5,30f));
        ydata.add(new Entry(6,30f));
        ydata.add(new Entry(7,80f));
        ydata.add(new Entry(8,100f));
        ydata.add(new Entry(9,50f));
        ydata.add(new Entry(10,3f));
        ydata.add(new Entry(11,5f));

        LineDataSet lineDataSet = new LineDataSet(ydata,"test");
        lineDataSet.setFillAlpha(65);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(3f);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
    }

    public void loadAttendChartData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        datalist.add(jsonObject.getString("attend"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
