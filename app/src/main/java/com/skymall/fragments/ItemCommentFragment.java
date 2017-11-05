package com.skymall.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skymall.R;
import com.skymall.adapters.DividerItemDecoration;
import com.skymall.adapters.ItemCommentAdapter;
import com.skymall.bean.CommentDetailBean;
import com.skymall.bean.Comments;
import com.skymall.bean.Items;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ItemCommentFragment  extends Fragment {

    private RecyclerView comments;
    private LayoutInflater mInflater;
    private ItemCommentAdapter commentAdapter;
    private List<CommentDetailBean> commentsList = new ArrayList<CommentDetailBean>();
    private TextView commentCountTv;
    private static final String COMMENT_INITIATED = "comment_initiated";
    private String commentCount;



    //ItemID is passed in by other activity(depends on which item you just clicked)
    private String itemID;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.obj.toString()) {
                case COMMENT_INITIATED:
                    Log.d("------", " testing ---- ");
                    commentCountTv.setText(commentCount);
                    commentAdapter = new ItemCommentAdapter(getContext(), commentsList);
                    comments.setAdapter(commentAdapter);
                    comments.getAdapter().notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Intent intent = this.getActivity().getIntent();
        itemID = intent.getStringExtra("itemID");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_comment, null);
        initViews(rootView);
        initCommentsDetails();
        return rootView;
    }

    private void initCommentsDetails(){
        BmobQuery<Comments> query = new BmobQuery<Comments>();
        Items item = new Items();
        item.setObjectId(itemID);
        query.addWhereEqualTo("item", new BmobPointer(item));
        query.include("user,post.author");
        query.order("createdAt");
        query.findObjects(new FindListener<Comments>() {
            @Override
            public void done(List<Comments> list, BmobException e) {
                if (e == null) {
                    for(Comments comment : list){
                        CommentDetailBean commentDetailBean = new CommentDetailBean(comment.getUser().getUsername(), comment.getContent(), comment.getCreatedAt());
                        commentsList.add(commentDetailBean);
                    }
                    commentCount = list.size() + "";
                    Message commentInitiated = new Message();
                    commentInitiated.obj = COMMENT_INITIATED;
                    handler.sendMessage(commentInitiated);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews(View view){
        comments = (RecyclerView)view.findViewById(R.id.comments);
        commentCountTv = (TextView)view.findViewById(R.id.comment_count);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        commentAdapter = new ItemCommentAdapter(getContext(), commentsList);
        comments.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        comments.setLayoutManager(layoutManager);
        comments.setAdapter(commentAdapter);
    }

}
