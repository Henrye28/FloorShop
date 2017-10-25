package com.example.henryye.floorshop.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.Items;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by dan on 17/10/24.
 */
public class SearchingPageListGridAdapter extends BaseAdapter {

    private ArrayList<Items> content_items = new ArrayList<>();

    public SearchingPageListGridAdapter(ArrayList<Items> content_items) {
        this.content_items = content_items;
    }

    @Override
    public int getCount() {
        return content_items.size();
    }

    @Override
    public Object getItem(int position) {
        return content_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GridItemViewHolder gridItemViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_single_view, null);
            gridItemViewHolder = new GridItemViewHolder();
            gridItemViewHolder.imageView = ButterKnife.findById(convertView, R.id.searching_list_image);
            gridItemViewHolder.title = ButterKnife.findById(convertView, R.id.searching_list_title);
            gridItemViewHolder.price = ButterKnife.findById(convertView, R.id.searching_list_price);
            convertView.setTag(gridItemViewHolder);
        } else {
            gridItemViewHolder = (GridItemViewHolder) convertView.getTag();
        }
        gridItemViewHolder.imageView.setImageURI(Uri.parse(content_items.get(position).getCover().getFileUrl()));
        gridItemViewHolder.title.setText(content_items.get(position).getName());
        gridItemViewHolder.price.setText(content_items.get(position).getPrice() + "");

        return convertView;
    }

    class GridItemViewHolder {
        private SimpleDraweeView imageView;
        private TextView title;
        private TextView price;
    }
}
