package com.skymall.bean;

public class CommentDetailBean {
    String username;
    String commentContent;
    String time;

    public CommentDetailBean(String username, String commentContent, String time){
        this.username = username;
        this.commentContent = commentContent;
        this.time = time;
    }


    public String getUsername(){
        return username;
    }

    public String getCommentContent(){
        return commentContent;
    }

    public String getTime(){
        return time;
    }


}
