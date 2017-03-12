package com.sayar.sila.bebetakvim.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayar.sila.bebetakvim.R;
import com.sayar.sila.bebetakvim.activities.Diaper;
import com.sayar.sila.bebetakvim.activities.DiaperActivity;
import com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util.Util;
import com.sayar.sila.bebetakvim.database.DiaperDataSource;

import java.util.List;

/**
 * Created by zisan on 20.09.2016.
 */
public class DiaperListAdapter  extends ArrayAdapter<Diaper> {
    private Context mContext;

    public DiaperListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mContext=context;
    }

    public DiaperListAdapter(Context context, int resource, List<Diaper> items) {
        super(context, resource, items);
        this.mContext=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.lunch_line, null);
        }

        final Diaper p = getItem(position);

        TextView tt1 = (TextView) v.findViewById(R.id.textView);
        ImageView sil = (ImageView) v.findViewById(R.id.lunch_del);

        tt1.setText(p.getTime() + "  " + p.getDiaper());
        tt1.setTypeface(Typeface.createFromAsset(getContext().getAssets(), Util.font));


        sil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DiaperDataSource.deleteDiaper(p);
                if (getContext() instanceof  DiaperActivity)
                    ((DiaperActivity)getContext()).listDayDiapers();
            }
        });

        return v;
    }

    public void setValues(List<Diaper> values) {
        clear();
        addAll(values);
    }

}
