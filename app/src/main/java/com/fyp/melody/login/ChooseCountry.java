package com.fyp.melody.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.melody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hananideen on 28/5/2015.
 */
public class ChooseCountry extends Activity{

    EditText Searchfield;
    ListView CountryListView;

    public static class Country {
        public String name;
        public String code;
        public String shortname;
    }

    CountryAdapter countryAdapter;
    CountryList Cl;
    List<CountryList.Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);

        Cl = new CountryList();
        countryList = Cl.ReadCountries();

        // initiliazing the country adapter
        countryAdapter = new CountryAdapter(countryList);
        // defining the view elements
        Searchfield = (EditText) findViewById(R.id.SearchText);
        CountryListView = (ListView) findViewById(R.id.CountrylistView);

        // assign the adapter to the list view
        CountryListView.setAdapter(countryAdapter);
        // set the listener of the listview for each item
        CountryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryList.Country country = countryAdapter.getCountry(position);

                Intent returnIntent = new Intent();

                returnIntent.putExtra("CountryName", country.name);
                returnIntent.putExtra("CountryCode", country.code);
                returnIntent.putExtra("CountryShortname", country.shortname);
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });

        Searchfield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<CountryList.Country> temp = new ArrayList<CountryList.Country>();
                int textLength = Searchfield.getText().length();
                temp.clear();
                for (int i = 0; i < countryList.size(); i++) {

                    if(textLength <= countryList.get(i).name.length()) {

                        if (Searchfield.getText().toString().equalsIgnoreCase((String)countryList.get(i).name.subSequence(0, textLength))) {
                            temp.add(countryList.get(i));
                        }
                    }
                }
                // set the new list view adapter according to the new list
                countryAdapter = new CountryAdapter(temp);
                CountryListView.setAdapter(countryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    public class CountryAdapter extends BaseAdapter {

        List<CountryList.Country> countryList;

        CountryAdapter (List<CountryList.Country> countryList) {
            this.countryList = countryList;
        }


        @Override
        public int getCount() {
            return countryList.size();
        }

        @Override
        public Object getItem(int position) {
            return countryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) ChooseCountry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.country_item, parent, false);
            }
            TextView countryName = (TextView) convertView.findViewById(R.id.countryName);
            TextView countryCode = (TextView) convertView.findViewById(R.id.countryCode);

            CountryList.Country countries = countryList.get(position);
            countryName.setText(countries.name);
            countryCode.setText("+"+countries.code);


            return convertView;
        }

        public CountryList.Country getCountry (int position){

            return countryList.get(position);
        }
    }
}
