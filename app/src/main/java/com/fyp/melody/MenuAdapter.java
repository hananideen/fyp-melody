package com.fyp.melody;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class MenuAdapter extends BaseAdapter {

    SharedPreferences settings;

    private Activity mactivity;
    private List<Menus> MenuList;
    private LayoutInflater inflater;
    private Menus curMenu;

    public MenuAdapter (Activity mactivity, List<Menus> MenuList){
        this.mactivity = mactivity;
        this.MenuList = MenuList;
    }

    @Override
    public int getCount() {
        return MenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return MenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {

            inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.menu_item, parent, false);

        }

        TextView menuDesc = (TextView) convertView.findViewById(R.id.Description);
        TextView menuPrice = (TextView) convertView.findViewById(R.id.Price);
        NetworkImageView menuCompImg = (NetworkImageView) convertView.findViewById(R.id.CompanyImage);

        curMenu = MenuList.get(position);

        menuDesc.setText(curMenu.getMenuName());
        menuPrice.setText(curMenu.getMenuPrice());


        Log.e("tag", ApplicationLoader.getIp(curMenu.getCompanyImage()));
        menuCompImg.setImageUrl(ApplicationLoader.getIp(curMenu.getCompanyImage()), VolleySingleton.getInstance().getImageLoader());
        switch (position) {
            case 3:
                menuCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 2:
                menuCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
                menuCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 0:
                menuCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        return convertView;
    }

    public Menus getMenus(int position) {

        return MenuList.get(position);
    }

}
