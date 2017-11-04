package com.skymall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skymall.R;

import java.util.List;

/**
 * Created by henryye on 5/14/17.
 */
public class CountryCodeListAdapter extends BaseAdapter {

    private List<String> countries;
    private LayoutInflater inflater;

    public CountryCodeListAdapter(List<String> countries, Context context) {
        this.countries = countries;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countries==null?0:countries.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.countries_list_item, null);
        String countries_code = (String)getItem(position);

        if(countries_code != null) {
            String[] divided = countries_code.split(" ");
            int len = divided.length;
            String codes = countries_code.split(" ")[len-1];
            String countries = countries_code.replace(" "+codes,"");

            TextView place = (TextView) view.findViewById(R.id.place);
            TextView code = (TextView) view.findViewById(R.id.code);
            place.setText(countries);
            code.setText(codes);
        }
        return view;
    }
}
