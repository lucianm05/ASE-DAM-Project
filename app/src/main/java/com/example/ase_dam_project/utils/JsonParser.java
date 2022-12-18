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
    private static final String NAME = "name";
    private static final String COMMON = "common";
    private static final String CAPITAL = "capital";
    private static final String POPULATION = "population";
    private static final String CONTINENTS = "continents";
    private static final String FLAGS = "flags";
    private static final String PNG = "png";
    private static final String CCA3 = "cca3";

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
           JSONObject nameObject = countryObject.getJSONObject(NAME);
           String name = nameObject.getString(COMMON);

           JSONArray capitalArray = countryObject.getJSONArray(CAPITAL);
           String capitalCity = capitalArray.getString(0);

           long population = countryObject.getLong(POPULATION);

           JSONArray continentsArray = countryObject.getJSONArray(CONTINENTS);
           String continentName = continentsArray.getString(0);

           JSONObject flagsObject = countryObject.getJSONObject(FLAGS);
           String flagUrl = flagsObject.getString(PNG);

           String cca3 = countryObject.getString(CCA3);

           return new Country(name, capitalCity, population, flagUrl, continentName, cca3);
       } catch(JSONException ex) {
           ex.printStackTrace();
           return null;
       }
    }
}
