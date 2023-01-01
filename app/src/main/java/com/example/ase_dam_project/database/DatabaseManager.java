package com.example.ase_dam_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ase_dam_project.database.daos.CapitalDao;
import com.example.ase_dam_project.database.daos.CountryDao;
import com.example.ase_dam_project.database.daos.CountryWithCapitalDao;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.utils.Constants;
import com.example.ase_dam_project.utils.JsonParser;
import com.example.ase_dam_project.utils.Validations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

@Database(entities = {Country.class, Capital.class}, exportSchema = false, version = 4)
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "dam_project_db";
    public static final String COUNTRIES = "countries";
    public static final String CAPITALS = "capitals";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if(databaseManager == null) {
            synchronized (Database.class) {
                if(databaseManager == null) {
                    databaseManager =
                            Room
                                .databaseBuilder(context, DatabaseManager.class, DatabaseManager.DB_NAME)
                                .addCallback(new Callback() {
                                    @Override
                                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                        super.onOpen(db);
                                        try {
                                            JSONArray countriesJSONArray = new JSONArray(JsonParser.readFromJson(
                                                    context.getAssets(),
                                                    "countries.json"));

                                            for(int i = 0; i < countriesJSONArray.length(); i++) {
                                                JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);
                                                Country country = JsonParser.getCountry(countryJSONObject);

                                                if(Validations.isValidCountry(country) && country.getCapital() != null) {

                                                    ContentValues capitalValues = new ContentValues();
                                                    capitalValues.put(Constants.NAME, country.getCapital().getName());

                                                    long capitalId = db.insert(DatabaseManager.CAPITALS,
                                                            SQLiteDatabase.CONFLICT_IGNORE,
                                                            capitalValues);

                                                    if(capitalId <= 0) continue;

                                                    country.setCapitalId(capitalId);

                                                    ContentValues countryValues = new ContentValues();
                                                    countryValues.put(Constants.NAME, country.getName());
                                                    countryValues.put(Constants.POPULATION, country.getPopulation());
                                                    countryValues.put(Constants.FLAG_URL, country.getFlagUrl());
                                                    countryValues.put(Constants.COF_URL, country.getCofUrl());
                                                    countryValues.put(Constants.CONTINENT_NAME, country.getContinentName());
                                                    countryValues.put(Constants.CCA3, country.getCca3());
                                                    countryValues.put(Constants.CAPITAL_ID, country.getCapitalId());

                                                    db.insert(DatabaseManager.COUNTRIES,
                                                            SQLiteDatabase.CONFLICT_IGNORE,
                                                            countryValues);

                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .fallbackToDestructiveMigration()
                                .build();
                }
            }
        }

        return databaseManager;
    }

    public abstract CountryDao getCountryDao();

    public abstract CapitalDao getCapitalDao();

    public abstract CountryWithCapitalDao getCountryWithCapitalDao();
}
