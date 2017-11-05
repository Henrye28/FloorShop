package com.skymall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skymall.R;
import com.skymall.widgets.SearchingMenuGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dan on 17/10/21.
 */
public class SearchingPageDropdownCommonAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = -1;
    private boolean[] chosenPostion;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public SearchingPageDropdownCommonAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        chosenPostion = new boolean[list.size()];
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int childCount = parent.getChildCount();
        ViewHolder viewHolder;

        if (position != childCount && position == 0 && convertView != null) {
            return convertView;
        }

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.searching_dropdown_block, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        if(((SearchingMenuGridView) parent).isOnMeasure){
            return convertView;
        }

        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                if (chosenPostion[checkItemPosition] == true) {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                    viewHolder.mText.setBackgroundResource(R.drawable.uncheck_bg);
                    chosenPostion[checkItemPosition] = false;
                } else {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                    viewHolder.mText.setBackgroundResource(R.drawable.check_bg);
                    chosenPostion[checkItemPosition] = true;
                }
            }
        }
    }

    public boolean[] getChosePosition() {
        return chosenPostion;
    }

    static class ViewHolder {
        @BindView(R.id.searching_text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
