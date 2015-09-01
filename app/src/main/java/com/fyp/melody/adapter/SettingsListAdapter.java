package com.fyp.melody.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fyp.melody.R;
import com.fyp.melody.model.Settings;

import java.util.List;

/**
 * Created by Hananideen on 2/6/2015.
 */
public class SettingsListAdapter extends BaseAdapter {

    private List<Settings> mProductList;
    private LayoutInflater mInflater;

    public SettingsListAdapter(List<Settings> list, LayoutInflater inflater) {
        mProductList = list;
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.settings_item, null);
            item = new ViewItem();

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.TextViewItem);

            item.productDescription = (TextView) convertView
                    .findViewById(R.id.textViewDescription);


            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        Settings curProduct = mProductList.get(position);

        item.productTitle.setText(curProduct.title);
        item.productDescription.setText(curProduct.description);


        return convertView;
    }

    private class ViewItem {
        TextView productTitle;
        TextView productDescription;
    }
}

