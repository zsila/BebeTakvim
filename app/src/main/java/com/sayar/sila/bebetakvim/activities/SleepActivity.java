package com.sayar.sila.bebetakvim.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.adapters.SleepListAdapter;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.HourUtil;
import com.sayar.sila.bebetakvim.database.SleepDataSource;
import com.sayar.sila.bebetakvim.database.SleeptimeDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zisan on 31.08.2016.
 */
public class SleepActivity extends Activity {
    Sleep sleep;
    Sleeptime sleeptime;
    String tmp;
    ImageView uyudu;
    ImageView uyandi;
    ImageView manuel;
    ImageView auto;
    ImageView tickman;
    TextView startEdit;
    TextView endEdit;
    ListView listView;
    String start = null;
    String end = null;
    Date startTime;
    Date endTime;
    long duration;
    private SleepDataSource datasource;
    private SleeptimeDataSource stDatasource;
    SleepListAdapter customAdapter;
    List<Sleep> values = new ArrayList<>();
    String today = CalendarUtil.today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep);

        listView = (ListView) findViewById(R.id.listView2);
        TextView tarih = (TextView) findViewById(R.id.textView8);
        uyudu = (ImageView) findViewById(R.id.ekle3);
        uyandi = (ImageView) findViewById(R.id.ekle4);
        manuel = (ImageView) findViewById(R.id.imageView8);
        manuel.setImageResource(R.drawable.rsz_1manuel);
        auto = (ImageView) findViewById(R.id.imageView9);
        auto.setImageResource(R.drawable.rsz_1otomatik);
        tickman = (ImageView) findViewById(R.id.imageView10);
        startEdit = (TextView) findViewById(R.id.editText7);
        endEdit = (TextView) findViewById(R.id.editText8);

        tarih.setText(today);

        stDatasource = new SleeptimeDataSource(getApplicationContext());
        stDatasource.open();

        if (!stDatasource.isEmpty()) {
            tmp = stDatasource.getSleeptime().getStart();
            if (stDatasource.getSleeptime().getStart().substring(tmp.length() - 1).equals("m")) {
                setViewVisiblity(View.VISIBLE, startEdit, endEdit, tickman);
                setViewVisiblity(View.INVISIBLE, manuel, auto);
                startEdit.setText(stDatasource.getSleeptime().toString().substring(0, stDatasource.getSleeptime().toString().length() - 2));
            } else if (stDatasource.getSleeptime().getStart().substring(tmp.length() - 1).equals("a")) {
                setViewVisiblity(View.VISIBLE, uyandi);
                setViewVisiblity(View.INVISIBLE, manuel, auto);
            }
        }

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewVisiblity(View.VISIBLE, uyudu);
                setViewVisiblity(View.INVISIBLE, uyandi, auto, startEdit, endEdit, tickman, manuel);
            }
        });

        manuel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setViewVisiblity(View.VISIBLE, startEdit, endEdit, tickman);
                setViewVisiblity(View.INVISIBLE, uyandi, uyudu, auto, manuel);


                stDatasource = new SleeptimeDataSource(getApplicationContext());
                stDatasource.open();

                if (!stDatasource.isEmpty()) {
                    tmp = stDatasource.getSleeptime().getStart();
                    if (tmp.substring(tmp.length() - 1).equals("m")) {
                        tmp = tmp.substring(0, tmp.length() - 2);
                        try {
                            startTime = new SimpleDateFormat("HH:mm").parse(tmp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else
                        tmp = "";
                } else {
                    tmp = "";
                }

                startEdit.setText(tmp);


            }
        });

        startEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(startEdit, "Saat:", "Uyudu", "İptal");
            }
        });

        endEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(endEdit, "Saat:", "Uyandı", "İptal");
            }

        });

        tickman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    endTime = new SimpleDateFormat("HH:mm").parse(endEdit.getText().toString());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                try {
                    startTime = new SimpleDateFormat("HH:mm").parse(startEdit.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (endTime != null && startTime != null) {
                    duration = endTime.getTime() - startTime.getTime();
                    duration /= 60000;
                    if (duration < 0)
                        duration += 1440;

                    String temp = "" + duration;

                    temp = HourUtil.minToHour(temp);
                    sleep = datasource.createSleep(temp, startEdit.getText().toString(), today);

                    listDaySleeps();
                    startEdit.setText("");
                    endEdit.setText("");
                    endTime = null;
                    startTime = null;
                    if (!stDatasource.isEmpty())
                        stDatasource.deleteSleeptime();

                }

                setViewVisiblity(View.INVISIBLE, endEdit, startEdit, tickman);
                setViewVisiblity(View.VISIBLE, manuel, auto);

            }
        });


        uyudu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uyudu.setVisibility(View.INVISIBLE);
                uyandi.setVisibility(View.VISIBLE);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                start = HourUtil.getHM(hour, minute);
                startEdit.setText(start);
                endEdit.setText("");

                try {
                    startTime = new SimpleDateFormat("HH:mm").parse(start);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!stDatasource.isEmpty())
                    stDatasource.deleteSleeptime();

                sleeptime = stDatasource.createSleeptime(start + "-a");
            }
        });

        uyandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                tmp = stDatasource.getSleeptime().getStart();
                start = tmp.substring(0, tmp.length() - 2);
                end = HourUtil.getHM(hour, minute);
                try {
                    endTime = new SimpleDateFormat("HH:mm").parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!stDatasource.isEmpty())
                    try {
                        startTime = new SimpleDateFormat("HH:mm").parse(start);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                if (endTime != null && startTime != null) {
                    duration = endTime.getTime() - startTime.getTime();
                    duration /= 60000;
                    if (duration < 0)
                        duration += 1440;

                    String temp = "" + duration;

                    temp = HourUtil.minToHour(temp);
                    sleep = datasource.createSleep(temp, start, today);

                    listDaySleeps();
                    startEdit.setText("");
                    endEdit.setText("");
                    endTime = null;
                    startTime = null;
                }

                setViewVisiblity(View.INVISIBLE, uyandi);
                setViewVisiblity(View.VISIBLE, manuel, auto);
                stDatasource.deleteSleeptime();
            }
        });


        datasource = new SleepDataSource(this);
        datasource.open();

        listDaySleeps();


    }


    public void listDaySleeps() {
        values = datasource.getDaySleeps(today);

        if (customAdapter == null) {
            customAdapter = new SleepListAdapter(this, R.layout.lunch_line, values);
            listView.setAdapter(customAdapter);
        } else {
            customAdapter.setValues(values);
        }
    }

    int pLastSelectedHour = 0;
    int pLastSelectedMinute = 0;

    public void showTimePicker(final TextView edit, String title, final String done, final String cancel) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        final TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                pLastSelectedHour = selectedHour;
                pLastSelectedMinute = selectedMinute;
            }
        }, hour, minute, true) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                super.onClick(dialog, which);
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    edit.setText(HourUtil.getHM(pLastSelectedHour, pLastSelectedMinute));
                    if (done.equals("Uyudu")) {
                        if (stDatasource.isEmpty()) {
                            sleeptime = stDatasource.createSleeptime(HourUtil.getHM(pLastSelectedHour, pLastSelectedMinute)+ "-m");
                        } else {
                            stDatasource.deleteSleeptime();
                            sleeptime = stDatasource.createSleeptime(HourUtil.getHM(pLastSelectedHour, pLastSelectedMinute) + "-m");
                        }
                    }
                }

            }
        };
        mTimePicker.setTitle(title);
        mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, done, mTimePicker);
        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, cancel, mTimePicker);
        mTimePicker.show();

    }

    public void setViewVisiblity(int visibility, View... imglist) {
        for (View img : imglist) {
            img.setVisibility(visibility);
        }
    }

}
