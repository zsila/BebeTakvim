package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.adapters.HealthListAdapter;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.TypefaceUtil;
import com.sayar.sila.bebetakvim.database.ControlDataSource;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zisan on 1.11.2016.
 */
public class HealthActivity extends Activity {

    Control control = null;
    private ControlDataSource datasource;
    String value = null;
    ArrayList<String> valuesB = new ArrayList<>();
    ArrayList<String> valuesK = new ArrayList<>();
    ArrayList<String> monthsName = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    List<Control> controls = new ArrayList<>();

    private String[] mMonth = new String[]{
            "Oca", "Şub", "Mar", "Nis", "May", "Haz",
            "Tem", "Ağu", "Eyl", "Eki", "Kas", "Ara"
    };


    DataPoint[] dataSetB;
    DataPoint[] dataSetK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health);

        ListView list = (ListView) findViewById(R.id.listView4);

        datasource = new ControlDataSource(this);
        datasource.open();

        controls = datasource.getAllControls();


        for (Control v : controls) {
            monthsName.add(CalendarUtil.getMonthName(v.getDate()));
            months.add(CalendarUtil.extractMonth(v.getDate()));
            valuesB.add(extractValue(v.getBoy()) + "/" + CalendarUtil.extractMonth(v.getDate()));
            valuesK.add(extractValue(v.getKilo()) + "/" + CalendarUtil.extractMonth(v.getDate()));
        }


        dataSetK = getDataPoints(valuesK);
        dataSetB = getDataPoints(valuesB);


        ArrayAdapter<String> adp = new HealthListAdapter(getApplicationContext(), monthsName);
        list.setAdapter(adp);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (controls.get(position).getBoy() != null) {
                    value = controls.get(position).getBoy();
                }
                if (controls.get(position).getKilo() != null) {
                    if (value != null)
                        value += "\n" + controls.get(position).getKilo();
                    else
                        value = "\n" + controls.get(position).getKilo();
                }
                if (controls.get(position).getNot() != null) {
                    if (value != null)
                        value += "\n" + controls.get(position).getNot();
                    else
                        value = "\n" + controls.get(position).getNot();
                }

                //value = controls.get(position).getBoy() + "\n" + controls.get(position).getKilo() + "\n\n" + controls.get(position).getNot();

                Intent i = new Intent(HealthActivity.this, HealthDetail.class);
                if (!value.isEmpty())
                    i.putExtra("NOT", value);
                startActivity(i);
            }
        });

        ImageView btn = (ImageView) findViewById(R.id.imageView6);
        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Draw the Income vs Expense Chart

                openChart(valuesK, valuesB, months);
            }
        };

        // Setting event click listener for the button btn_chart of the MainActivity layout
        btn.setOnClickListener(clickListener);
    }

    private void openChart(ArrayList<String> listK, ArrayList<String> listB, List<String> listM) {
        Double[] listkg = getPoints(listK);
        Double[] listBoy =getPoints(listB);

        String[] months = new String[listM.size()];
        for (int i = 0; i < months.length; i++) {
            months[i] = listM.get(i);
        }

        // Creating an  XYSeries for Income
        XYSeries kilo = new XYSeries("Kilo");
        // Creating an  XYSeries for Expense
        XYSeries boy = new XYSeries("Boy");
        // Adding data to Income and Expense Series
        for (int i = 0; i < listkg.length; i++) {
            kilo.add(i + 1, listkg[i]);
            boy.add(i + 1, listBoy[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(kilo);
        // Adding Expense Series to dataset
        dataset.addSeries(boy);

        // Creating XYSeriesRenderer to customize weight
        XYSeriesRenderer weight = new XYSeriesRenderer();
        weight.setColor(Color.parseColor("#A83D7B"));
        weight.setPointStyle(PointStyle.CIRCLE);
        weight.setFillPoints(true);
        weight.setLineWidth(2);
        weight.setDisplayChartValues(true);
        weight.setChartValuesTextSize(15);


        // Creating XYSeriesRenderer to customize height
        XYSeriesRenderer height = new XYSeriesRenderer();
        height.setColor(Color.parseColor("#186EA3"));
        height.setPointStyle(PointStyle.CIRCLE);
        height.setFillPoints(true);
        height.setLineWidth(2);
        height.setDisplayChartValues(true);
        height.setChartValuesTextSize(15);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);

        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //multiRenderer.setXTitle("Ay");
        multiRenderer.setZoomButtonsVisible(true);
        for (int i = 0; i < months.length; i++) {
            multiRenderer.addXTextLabel(i + 1, (mMonth[Integer.parseInt(months[i]) - 1]));
        }
        multiRenderer.setLabelsTextSize(15);

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(weight);
        multiRenderer.addSeriesRenderer(height);

        // Creating an intent to plot line chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);

        // Start Activity
        startActivity(intent);
    }


    @NonNull
    private DataPoint[] getDataPoints(List<String> values) {
        DataPoint[] dataSet = new DataPoint[values.size()];
        for (int i = 0; i < values.size(); i++) {
            if (dataSet[i] != null)
                dataSet[i] = new DataPoint(Double.parseDouble(extractSecond(values.get(i))), Double.parseDouble(extractFirst(values.get(i))));
        }
        return dataSet;
    }

    @NonNull
    private Double[] getPoints(List<String> values) {
        Double[] dataSet = new Double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            if (extractFirst(values.get(i)) != null)
                dataSet[i] = Double.parseDouble(extractFirst(values.get(i)));
        }
        return dataSet;
    }

    public static String extractValue(String txt) {
        String[] items = txt.split(" ");
        if (items[0].equals(""))
            return Integer.toString(0);
        else
            return items[0];
    }

    public static String extractSecond(String txt) {
        String[] items = txt.split("\\/");
        return items[1];
    }

    public static String extractFirst(String txt) {
        String[] items = txt.split("\\/");
        return items[0];
    }

}
