package com.example.ase_dam_project.database.services;

import android.content.Context;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.daos.CapitalDao;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.utils.Validations;

import java.util.concurrent.Callable;

public class CapitalService {
    private final CapitalDao capitalDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CapitalService(Context context) {
        this.capitalDao = DatabaseManager.getInstance(context).getCapitalDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Capital capital, Callback<Capital> activityThread) {
        Callable<Capital> insertOperation = new Callable<Capital>() {
            @Override
            public Capital call() {
                if(capital == null ||
                    !Validations.isStringValid(capital.getName()) ||
                    capital.getId() != 0) {
                    return null;
                }

                long id = capitalDao.insert(capital);

                if(id < 0) return null;

                capital.setId(id);
                return capital;
            }
        };

        asyncTaskRunner.executeAsync(insertOperation, activityThread);
    }
}
