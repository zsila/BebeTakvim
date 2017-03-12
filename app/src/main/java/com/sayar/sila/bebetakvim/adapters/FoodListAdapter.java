package com.sayar.sila.bebetakvim.adapters;

import com.sayar.sila.bebetakvim.activities.Food;
import com.sayar.sila.bebetakvim.activities.FoodActivity;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.CalendarUtil;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.FoodDataSource;

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

import java.util.List;

/**
 * Created by zisan on 19.08.2016.
 */
public class FoodListAdapter extends ArrayAdapter<Food> {

    public FoodListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FoodListAdapter(Context context, int resource, List<Food> items) {
        super(context, resource, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.lunch_line, null);
        }

        final Food p = getItem(position);
        TextView tt1 = (TextView) v.findViewById(R.id.textView);
        ImageView sil = (ImageView) v.findViewById(R.id.lunch_del);

        tt1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), Util.font));

        tt1.setText(p.getTime() + "  " + p.getComment());

        sil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FoodDataSource.deleteComment(p);
                if (getContext() instanceof  FoodActivity)
                    ((FoodActivity)getContext()).listDayComments(CalendarUtil.today);
            }
        });

        return v;
    }

    public void setValues(List<Food> values) {
        clear();
        addAll(values);
    }
}
