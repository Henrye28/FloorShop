package com.example.henryye.floorshop.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.Items;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dan on 17/10/22.
 */
public class SearchingPageListRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private ArrayList<Items> content_items = new ArrayList<>();

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public SearchingPageListRecyclerAdapter(ArrayList<Items> content_items) {
        this.content_items = content_items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_single_view, parent, false);
        ReItemViewHolder reItemViewHolder = new ReItemViewHolder(view);
        view.setOnClickListener(this);
        return reItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReItemViewHolder) holder).image.setImageURI(Uri.parse(content_items.get(position).getCover().getFileUrl()));
        ((ReItemViewHolder) holder).title.setText(content_items.get(position).getName());
        ((ReItemViewHolder) holder).price.setText(content_items.get(position).getPrice() + "");
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

    class ReItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.searching_list_image)
        SimpleDraweeView image;

        @InjectView(R.id.searching_list_title)
        TextView title;

        @InjectView(R.id.searching_list_price)
        TextView price;

        public ReItemViewHolder(View itemView) {
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
