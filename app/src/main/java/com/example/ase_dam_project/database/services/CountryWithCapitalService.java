package com.example.ase_dam_project.database.services;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.daos.CountryWithCapitalDao;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.entities.Filters;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.utils.Validations;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

public class CountryWithCapitalService {
    private final CountryWithCapitalDao countryWithCapitalDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CountryWithCapitalService(Context context) {
        this.countryWithCapitalDao = DatabaseManager.getInstance(context).getCountryWithCapitalDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Country country, Capital capital, Callback<CountryWithCapital> activityThread) {
        Callable<CountryWithCapital> insertOperation = new Callable<CountryWithCapital>() {
            @Override
            public CountryWithCapital call() throws Exception {
                if(!Validations.isValidCountry(country) ||
                    !Validations.isValidCapital(capital) ||
                    country.getId() != 0 ||
                    capital.getId() != 0) {
                    Log.i("INSERT OPERATION", "INVALID VALUES");
                    Log.i("IS VALID COUNTRY", Validations.isValidCountry(country).toString());
                    Log.i("IS VALID CAPITAL", Validations.isValidCapital(capital).toString());
                    Log.i("COUNTRY ID", String.valueOf(country.getId()));
                    Log.i("CAPITAL ID", String.valueOf(capital.getId()));
                    return null;
                }

                long capitalId = countryWithCapitalDao.insertCapital(capital);

                if(capitalId < 0) {
                    Log.i("INSERT OPERATION", "CAPITAL ID IS < 0");
                    return null;
                }

                capital.setId(capitalId);
                country.setCapitalId(capitalId);

                long countryId = countryWithCapitalDao.insertCountry(country);

                if(countryId < 0) {
                    Log.i("INSERT OPERATION", "COUNTRY ID IS < 0");
                    return null;
                }

                country.setId(countryId);

                return new CountryWithCapital(country, capital);
            }
        };

        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }

    public void getAll(@Nullable Filters filters, Callback<List<CountryWithCapital>> activityThread) {
        Callable<List<CountryWithCapital>> getAllOperation = new Callable<List<CountryWithCapital>>() {
            @Override
            public List<CountryWithCapital> call() {
                String countryName = "%" + filters.getCountryName() + "%";

                return countryWithCapitalDao.getAll(countryName);
            }
        };

        asyncTaskRunner.executeAsync(getAllOperation, activityThread);
    }

    public void getCountryWithCapitalByName(@Nullable String countryName, Callback<CountryWithCapital> activityThread) {
        Callable<CountryWithCapital> getOperation = new Callable<CountryWithCapital>() {
            @Override
            public CountryWithCapital call() throws Exception {
                return countryWithCapitalDao.getCountryWithCapitalByName(countryName);
            }
        };

        asyncTaskRunner.executeAsync(getOperation, activityThread);
    }

    public void count(Callback<Integer> activityThread) {
        Callable<Integer> countOperation = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return countryWithCapitalDao.count();
            }
        };

        asyncTaskRunner.executeAsync(countOperation, activityThread);
    }

    public void getRandomCountryWithCapital(Callback<CountryWithCapital> activityThread) {
        Callable<CountryWithCapital> getOperation = new Callable<CountryWithCapital>() {
            @Override
            public CountryWithCapital call() throws Exception {
                int count = countryWithCapitalDao.count();

                if(count <= 0) return null;

                Random random = new Random();
                int randomInt = random.nextInt(count);

                return countryWithCapitalDao.getCountryWithCapital(randomInt);
            }
        };

        asyncTaskRunner.executeAsync(getOperation, activityThread);
    }

    public void getContinentsCount(Callback<Map<String, Integer>> activityThread) {
        Callable<Map<String, Integer>> getOperation = new Callable<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> call() throws Exception {
                return countryWithCapitalDao.getContinentsCount();
            }
        };

        asyncTaskRunner.executeAsync(getOperation, activityThread);
    }
}
