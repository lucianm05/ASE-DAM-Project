package com.example.ase_dam_project.database.services;

import android.content.Context;
import android.util.Log;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.daos.CountryWithCapitalDao;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;

import java.util.List;
import java.util.concurrent.Callable;

public class CountryWithCapitalService {
    private final CountryWithCapitalDao countryWithCapitalDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CountryWithCapitalService(Context context) {
        this.countryWithCapitalDao = DatabaseManager.getInstance(context).getCountryWithCapitalDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<CountryWithCapital>> activityThread) {
        Callable<List<CountryWithCapital>> getAllOperation = new Callable<List<CountryWithCapital>>() {
            @Override
            public List<CountryWithCapital> call() {
                Log.i("CTYWITHCPT CALL", "CALL");
                return countryWithCapitalDao.getAll();
            }
        };

        asyncTaskRunner.executeAsync(getAllOperation, activityThread);
    }
}
