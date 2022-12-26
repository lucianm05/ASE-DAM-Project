package com.example.ase_dam_project.utils;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonParser {
    public static String readFromJson(AssetManager assetManager, String fileName) {
        try {
            InputStream in = assetManager.open(fileName);
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

           Capital capital = JsonParser.getCapital(countryObject);

           long population = countryObject.getLong(Constants.POPULATION);

           JSONArray continentsArray = countryObject.getJSONArray(Constants.CONTINENTS);
           String continentName = continentsArray.getString(0);

           JSONObject flagsObject = countryObject.getJSONObject(Constants.FLAGS);
           String flagUrl = flagsObject.getString(Constants.PNG);

           JSONObject cofObject = countryObject.getJSONObject(Constants.COAT_OF_ARMS);
           String cofUrl = cofObject.getString(Constants.PNG);

           String cca3 = countryObject.getString(Constants.CCA3);

           return new Country(name, capital, population, flagUrl, cofUrl, continentName, cca3);
       } catch(JSONException ex) {
           ex.printStackTrace();
           return null;
       } catch(NullPointerException ex) {
           ex.printStackTrace();
           return null;
       }
    }

    public static Capital getCapital(JSONObject countryObject) {
        try {
            JSONArray capitalArray = countryObject.getJSONArray(Constants.CAPITAL);
            String capitalName = capitalArray.getString(0);

            return new Capital(capitalName);
        } catch(JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
