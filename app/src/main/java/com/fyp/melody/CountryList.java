package com.fyp.melody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hananideen on 28/5/2015.
 */
public class CountryList {

    private HashMap<String, String> CodeMap;        // the key is CODE the value is COUNTRY
    private HashMap<String, String> CountryMap;     // the key is COUNTRY the value is CODE
    private HashMap<String, String> LanguageMap;    // the key is COUNTRY the value is SHORTNAME
    ArrayList<String> countryArray;

    CountryList (){

        CodeMap = new HashMap<>();
        CountryMap = new HashMap<>();
        LanguageMap = new HashMap<>();
        countryArray = new ArrayList<>();
    }

    public static class Country {
        public String name;
        public String code;
        public String shortname;
    }

    public List<Country> ReadCountries()
    {
        List<Country> countries = new ArrayList<Country>();

        try {
            BufferedReader buffer = new BufferedReader( new InputStreamReader(ApplicationLoader.getContext().getResources().getAssets().open("countries.txt")));
            String line;
            while ((line = buffer.readLine()) !=null)
            {
                Country country = new Country();
                String[] arg = line.split(";");
                country.name = arg[2];
                country.code = arg[0];
                country.shortname = arg[1];
                CodeMap.put(arg[0],arg[2]);
                CountryMap.put(arg[2],arg[0]);
                LanguageMap.put(arg[2], arg[1]);
                countryArray.add(0,arg[2]);

                countries.add(country);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }



        return countries;
    }

    public HashMap<String, String> getCodemap(){
        return CodeMap;
    }
    public HashMap<String , String > getCountryMap(){
        return CountryMap;
    }
    public HashMap<String , String > getLanguageMap () {
        return LanguageMap;
    }

    public void Clear(){
        CodeMap.clear();
        CountryMap.clear();
        LanguageMap.clear();
    }
}
