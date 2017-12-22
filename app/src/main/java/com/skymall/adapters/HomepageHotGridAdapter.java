package com.skymall.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.skymall.R;
import com.skymall.bean.HomePageHotItems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dan.ge on 2017/12/22.
 */

public class HomepageHotGridAdapter extends ArrayAdapter<HomePageHotItems> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<HomePageHotItems> items = new ArrayList<>();

    public HomepageHotGridAdapter(@NonNull Context context, int resource, ArrayList<HomePageHotItems> items) {
        super(context, resource);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.items = items;
    }

    public void setGridData(ArrayList<HomePageHotItems> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.homepage_grid_hot_text);
            holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.homepage_grid_hot_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomePageHotItems homePageHotItems = getItem(position);
        holder.textView.setText(homePageHotItems.getText());
        holder.imageView.setImageURI(Uri.parse(homePageHotItems.getBitmap().getFileUrl()));

        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        SimpleDraweeView imageView;
    }
}
