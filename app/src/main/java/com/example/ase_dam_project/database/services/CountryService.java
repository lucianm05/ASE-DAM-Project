package com.example.ase_dam_project.database.services;

import android.content.Context;
import android.util.Log;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.daos.CountryDao;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.utils.Validations;

import java.util.List;
import java.util.concurrent.Callable;

public class CountryService {
    private final CountryDao countryDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CountryService(Context context) {
        this.countryDao = DatabaseManager.getInstance(context).getCountryDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Country country, Callback<Country> activityThread) {
        Callable<Country> insertOperation = new Callable<Country>() {
            @Override
            public Country call() {
                if(country == null ||
                    !Validations.isStringValid(country.getName()) ||
                    !Validations.isStringValid(country.getContinentName()) ||
                    country.getId() != 0) {
                    return null;
                }

                long id = countryDao.insert(country);

                if(id < 0) return null;

                country.setId(id);
                return country;
            }
        };

        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }

    public void getAll(Callback<List<Country>> activityThread) {
        Callable<List<Country>> getAllOperation = new Callable<List<Country>>() {
            @Override
            public List<Country> call() {
                return countryDao.getAll();
            }
        };

        asyncTaskRunner.executeAsync(getAllOperation, activityThread);
    }

    public void count(Callback<Integer> activityThread) {
        Callable<Integer> countOperation = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return countryDao.count();
            }
        };
    }
}
