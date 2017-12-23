package com.skymall.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownButton extends TextView implements View.OnClickListener{

    private Context mContext;
    private OnClickListener mOnClickListener;
    private Timer mTimer;
    private TimerTask mTask;
    private long duration = 60000;
    private long temp_duration;
    private String clickBeffor = "Send Code";
    private String clickAfter = "Expired in ";

    public CountDownButton(Context context) {
        super(context);
        mContext = context;
        setOnClickListener(this);
    }

    public CountDownButton(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        setOnClickListener(this);
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            CountDownButton.this.setText(clickAfter + " " + temp_duration/1000 );
            temp_duration -= 1000;
            if (temp_duration < 0) {
                CountDownButton.this.setEnabled(true);
                CountDownButton.this.setText(clickBeffor);
                stopTimer();
            }
        }
    };


    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener instanceof CountDownButton) {
            super.setOnClickListener(onClickListener);
        }else{
            this.mOnClickListener = onClickListener;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);
        }
        //startTimer();
    }

    public void startTimer() {
        temp_duration = duration;
        CountDownButton.this.setEnabled(false);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x01);
            }
        };
        mTimer.schedule(mTask, 0, 1000);
    }

    public void stopTimer(){
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }
}
