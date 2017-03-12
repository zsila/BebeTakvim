package com.sayar.sila.bebetakvim.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;

import java.util.ArrayList;

/**
 * Created by zisan on 16.11.2016.
 */
public class HealthListAdapter extends ArrayAdapter<String> {
    public HealthListAdapter(Context context, ArrayList<String> months) {
        super(context, 0, months);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String month = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.health_line, parent, false);
        }
        // Lookup view for data population
        TextView monthName = (TextView) convertView.findViewById(R.id.textView15);
        monthName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), Util.font));

        // Populate the data into the template view using the data object
        monthName.setText(month);
        // Return the completed view to render on screen
        return convertView;
    }
}