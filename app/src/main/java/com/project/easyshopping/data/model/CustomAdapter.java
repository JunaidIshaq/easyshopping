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

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view Holder class */
    private class ViewHolder {

        private TextView title;
        private ImageView imageLink;
        private TextView description;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
          if(convertView == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflator.inflate(R.layout.listview, null);
          }
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.imageLink = convertView.findViewById(R.id.image);
            viewHolder.description = convertView.findViewById(R.id.description);
            RowItem row_pos = rowItems.get(position);
            viewHolder.imageLink.setImageResource(row_pos.getImage());
            viewHolder.title.setText(row_pos.getTitle());
            viewHolder.description.setText(row_pos.getLink());
            convertView.setTag(viewHolder);
        return convertView;
    }

}
