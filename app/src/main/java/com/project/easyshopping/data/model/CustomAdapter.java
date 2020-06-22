package com.project.easyshopping.data.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.easyshopping.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
            convertView = mInflator.inflate(R.layout.activity_listview, null);
          }
            viewHolder = new ViewHolder();

            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.imageLink = convertView.findViewById(R.id.image);
            viewHolder.description = convertView.findViewById(R.id.description);
//             Rect rect = new Rect();
//             rect.contains(100, 100, 100, 100);
//             RowItem row_pos = rowItems.get(position);
//             Bitmap bitmap = null;
//             try {
//                 bitmap = BitmapFactory.decodeStream(new URL(row_pos.getImage().getThumbnailLink()).openConnection().getInputStream());
//             } catch (MalformedURLException e) {
//                e.printStackTrace();
//             } catch (IOException e) {
//                e.printStackTrace();
//             }
            RowItem row_pos = rowItems.get(position);
            viewHolder.imageLink.setImageResource(row_pos.getImage());
            viewHolder.title.setText(row_pos.getTitle());
            viewHolder.description.setText(row_pos.getLink());
            convertView.setTag(viewHolder);
        return convertView;
    }

    public static Bitmap decodeSampleBitmapFromResource(Context context, String file, int reqWidth, int reqHeight) throws Exception {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        FileInputStream in = context.openFileInput(file);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();// ww  w .  j  a v a  2s  .c om
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        in = context.openFileInput(file);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
        in.close();
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int height = options.outHeight;
        int inSampleSize = 1;

        if (height <= reqHeight) {
            inSampleSize = 1;
        } else {
            inSampleSize = height / reqHeight;
        }

        return inSampleSize;
    }
}
