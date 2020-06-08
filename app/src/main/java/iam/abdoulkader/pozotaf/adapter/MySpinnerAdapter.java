package iam.abdoulkader.pozotaf.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by root on 18/09/17.
 */

public class MySpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {

    private final Helper mDropDownHelper;

    private Typeface mTypeface;

    public MySpinnerAdapter(Context context, String[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        context = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), "font/roboto/Roboto-Light.ttf");
        mDropDownHelper = new Helper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = super.getDropDownView(position, convertView, parent);
        ((TextView) v).setTypeface(mTypeface);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //((TextView) v).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setTypeface(mTypeface);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }
}