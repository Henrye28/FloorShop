package com.skymall.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.skymall.R;
import com.skymall.bean.HomePageHotItems;

import java.util.ArrayList;

/**
 * Created by dan.ge on 2017/12/22.
 */

public class HomepageHotGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<HomePageHotItems> items = null;

    public HomepageHotGridAdapter(@NonNull Context context, ArrayList<HomePageHotItems> items) {
        this.mContext = context;
        this.items = items;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.homepage_tab_grid_hot, null);
            holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.homepage_grid_hot_image);
            holder.textView = (TextView) convertView.findViewById(R.id.homepage_grid_hot_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(items.get(position).getName());
        holder.imageView.setImageURI(Uri.parse(items.get(position).getBitmap().getFileUrl()));

        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        SimpleDraweeView imageView;
    }
}
