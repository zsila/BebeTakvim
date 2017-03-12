package com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;

/**
 * Created by zisan on 13.10.2016.
 */
public class DialogUtil {

    public static void showDialog(final TextView txt, String title, int minFirst, int maxFirst, int minSec, int maxSec, final String unit, Activity context) {
        Dialog d = new Dialog(context);
        d.setTitle(title);
        View npView = context.getLayoutInflater().inflate(R.layout.npdialog, null);
        final NumberPicker minPicker = (NumberPicker) npView.findViewById(R.id.min_picker);
        minPicker.setMaxValue(maxFirst);
        minPicker.setMinValue(minFirst);
        if (txt.getText() != null  && !txt.getText().equals(""))
            minPicker.setValue(Integer.parseInt(txt.getText().toString().substring(0, txt.getText().toString().indexOf("."))));

        final NumberPicker maxPicker = (NumberPicker) npView.findViewById(R.id.max_picker);
        maxPicker.setMaxValue(maxSec);
        maxPicker.setMinValue(minSec);
        if (txt.getText() != null  && !txt.getText().equals(""))
            maxPicker.setValue(Integer.parseInt(txt.getText().toString().substring(txt.getText().toString().indexOf(".")+1, txt.getText().toString().indexOf(" "))));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(npView);
        builder.setPositiveButton("Tamam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        txt.setText(String.valueOf(minPicker.getValue()) + "." + maxPicker.getValue() + " " + unit);

                    }
                });
        final Dialog finalD = d;
        builder.setNegativeButton("İptal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finalD.dismiss();
                    }
                });
        d = builder.create();
        d.show();

    }

    public static void showCm(final TextView txt, String title, int min, int max, final String unit, Activity context) {
        Dialog d = new Dialog(context);
        String s=txt.getText().toString();
        d.setTitle(title);
        View npView = context.getLayoutInflater().inflate(R.layout.dialog, null);
        final NumberPicker minPicker = (NumberPicker) npView.findViewById(R.id.numberPicker1);
        minPicker.setMaxValue(max);
        minPicker.setMinValue(min);
        if(txt.getText()!=null && !txt.getText().equals(""))
            minPicker.setValue(Integer.parseInt(txt.getText().toString().substring(0, txt.getText().toString().indexOf(" "))));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(npView);
        builder.setPositiveButton("Tamam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        txt.setText(String.valueOf(minPicker.getValue()) + " " + unit);
                    }
                });
        final Dialog finalD = d;
        builder.setNegativeButton("İptal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finalD.dismiss();
                    }
                });
        d = builder.create();
        d.show();

    }
}
