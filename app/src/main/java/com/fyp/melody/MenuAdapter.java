package com.fyp.melody;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Hananideen on 1/7/2015.
 */
public class MenuAdapter extends BaseAdapter {

    SharedPreferences settings;

    private Activity mactivity;
    private List<Menus> PromoList;
    private LayoutInflater inflater;
    private boolean isLikeButtonClicked = false, isDislikeButtonClicked= false;
    private Menus curPromo;

    public MenuAdapter (Activity mactivity, List<Menus> PromoList){
        this.mactivity = mactivity;
        this.PromoList = PromoList;
    }

    @Override
    public int getCount() {
        return PromoList.size();
    }

    @Override
    public Object getItem(int position) {
        return PromoList.get(position);
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

        TextView promoDesc = (TextView) convertView.findViewById(R.id.Description);
        TextView promoPrice = (TextView) convertView.findViewById(R.id.Price);
        NetworkImageView promoCompImg = (NetworkImageView) convertView.findViewById(R.id.CompanyImage);

        curPromo = PromoList.get(position);

        promoDesc.setText(curPromo.getDescription());
        promoPrice.setText(curPromo.getPromoPrice());
//        promoDueDate.setText(curPromo.getDueDate());

//        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        boolean promotionImage = settings.getBoolean("promotionPreference",true);
//        if (promotionImage == true) {

        Log.e("tag", ApplicationLoader.getIp(curPromo.getPromotionImage()));
        promoCompImg.setImageUrl(ApplicationLoader.getIp(curPromo.getPromotionImage()), VolleySingleton.getInstance().getImageLoader());
        switch (position) {
            case 3:
//                    promoImage.setImageResource(R.drawable.hadramawt);
                promoCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 2:
//                    promoImage.setImageResource(R.drawable.sanaa);
                promoCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
//                    promoImage.setImageResource(R.drawable.dsedap);
                promoCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
            case 0:
//                    promoImage.setImageResource(R.drawable.noodles);
                promoCompImg.setImageResource(R.mipmap.ic_launcher);
                break;
        }
//        }


        //promoImage.setImageDrawable(curPromo.PromotionImage);
        //promoCompImg.setImageDrawable(curPromo.CompanyImage);

        return convertView;
    }

    public Menus getPromo(int position) {

        return PromoList.get(position);
    }

}
