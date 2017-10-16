package com.example.henryye.floorshop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henryye.floorshop.R;
import com.example.henryye.floorshop.bean.CommentDetailBean;

import java.util.List;

public class ItemCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<CommentDetailBean> data;
    private LayoutInflater Inflater;

    public ItemCommentAdapter(Context context, List<CommentDetailBean> data){
        this.context = context;
        this.data = data;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder viewHolder = new MyHolder(Inflater.inflate(R.layout.comment_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder) holder).username.setText(data.get(position).getUsername());
        ((MyHolder) holder).time.setText(data.get(position).getTime());
        ((MyHolder) holder).content.setText(data.get(position).getCommentContent());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView username, time, content;
        public MyHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.comment);
        }
    }

}
