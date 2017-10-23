package com.example.henryye.floorshop.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.Items;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dan on 17/10/22.
 */
public class SearchingPageListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ArrayList<Items> content_items = new ArrayList<>();

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public SearchingPageListAdapter(ArrayList<Items> content_items) {
        this.content_items = content_items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_single_view, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        view.setOnClickListener(this);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).image.setImageResource(content_items.get(position).getImageSrc());
        ((ItemViewHolder) holder).title.setText(content_items.get(position).getName());
        ((ItemViewHolder) holder).price.setText(content_items.get(position).getPrice() + "");
        holder.itemView.setTag(content_items.get(position));
    }

    @Override
    public int getItemCount() {
        return content_items.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(v, (Items) v.getTag());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.searching_list_image)
        ImageView image;

        @InjectView(R.id.searching_list_title)
        TextView title;

        @InjectView(R.id.searching_list_price)
        TextView price;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Items content);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
