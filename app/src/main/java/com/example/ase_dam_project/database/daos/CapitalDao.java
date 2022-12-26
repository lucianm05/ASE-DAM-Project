package com.example.ase_dam_project.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.entities.Capital;

@Dao
public interface CapitalDao {
    @Insert
    long insert(Capital capital);

    @Query("DELETE FROM " + DatabaseManager.COUNTRIES)
    public void deleteAllCapitals();
}
