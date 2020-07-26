package com.project.easyshopping.data.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.easyshopping.R;

import java.util.List;

public class CustomHistoryAdapter extends BaseAdapter {

    Context context;
    List<HistoryRowItem> historyRowItemList;

    public CustomHistoryAdapter(Context context, List<HistoryRowItem> historyRowItemList) {
        this.context = context;
        this.historyRowItemList = historyRowItemList;
    }

    @Override
    public int getCount() {
        return historyRowItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyRowItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historyRowItemList.indexOf(getItem(position));
    }

    /* private view Holder class */
    private class ViewHolder {

        private TextView title;
        private TextView link;
        private TextView date;
        private ImageView image;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflator.inflate(R.layout.history_listview, null);
        }
        viewHolder = new ViewHolder();
        viewHolder.title = convertView.findViewById(R.id.title);
        viewHolder.image = convertView.findViewById(R.id.image);
        viewHolder.link = convertView.findViewById(R.id.description);
        viewHolder.date = convertView.findViewById(R.id.date);
        HistoryRowItem row_pos = historyRowItemList.get(position);
        viewHolder.image.setImageResource(row_pos.getImage());
        viewHolder.title.setText(row_pos.getTitle());
        viewHolder.link.setText(row_pos.getLink());
        viewHolder.date.setText(row_pos.getDateTime());
        convertView.setTag(viewHolder);
        return convertView;
    }

}
