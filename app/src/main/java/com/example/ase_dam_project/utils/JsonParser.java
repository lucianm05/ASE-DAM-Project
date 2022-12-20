package com.example.ase_dam_project.utils;

import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ase_dam_project.entities.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonParser {
    public static String readFromJson(AppCompatActivity activity, String fileName) {
        try {
            InputStream in = activity.getAssets().open(fileName);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch(IOException ex) {
            return null;
        }
    }

    public static Country getCountry(JSONObject countryObject) {
       try {
           JSONObject nameObject = countryObject.getJSONObject(Constants.NAME);
           String name = nameObject.getString(Constants.COMMON);

           JSONArray capitalArray = countryObject.getJSONArray(Constants.CAPITAL);
           String capitalCity = capitalArray.getString(0);

           long population = countryObject.getLong(Constants.POPULATION);

           JSONArray continentsArray = countryObject.getJSONArray(Constants.CONTINENTS);
           String continentName = continentsArray.getString(0);

           JSONObject flagsObject = countryObject.getJSONObject(Constants.FLAGS);
           String flagUrl = flagsObject.getString(Constants.PNG);

           JSONObject cofObject = countryObject.getJSONObject(Constants.COAT_OF_ARMS);
           String cofUrl = cofObject.getString(Constants.PNG);

           String cca3 = countryObject.getString(Constants.CCA3);

           return new Country(name, capitalCity, population, flagUrl, cofUrl, continentName, cca3);
       } catch(JSONException ex) {
           ex.printStackTrace();
           return null;
       }
    }
}
