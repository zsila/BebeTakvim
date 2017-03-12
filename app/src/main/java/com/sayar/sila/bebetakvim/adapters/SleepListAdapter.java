package com.sayar.sila.bebetakvim.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.activities.Sleep;
import com.sayar.sila.bebetakvim.activities.SleepActivity;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.SleepDataSource;

import java.util.List;

/**
 * Created by zisan on 2.09.2016.
 */
public class SleepListAdapter extends ArrayAdapter<Sleep>{
        public SleepListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public SleepListAdapter(Context context, int resource, List<Sleep> items) {
            super(context, resource, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null ) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.lunch_line, null);
            }

            final Sleep p = getItem(position);

            TextView tt1 = (TextView) v.findViewById(R.id.textView);
            ImageView sil = (ImageView) v.findViewById(R.id.lunch_del);

            tt1.setText(p.getTime()+"   -   "+p.getDuration());
            tt1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), Util.font));


            sil.setOnClickListener( new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    SleepDataSource.deleteSleep(p);
                    if (getContext() instanceof  SleepActivity)
                        ((SleepActivity)getContext()).listDaySleeps();

                }
            });

            return v;
        }

        public void setValues(List<Sleep> values) {
            clear();
            addAll(values);
        }


}
