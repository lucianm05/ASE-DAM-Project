package com.example.ase_dam_project.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.entities.Country;

import java.util.List;

@Dao
public interface CountryDao {
    @Insert
    long insert(Country country);

    @Query("SELECT * FROM " + DatabaseManager.COUNTRIES)
    List<Country> getAll();

    @Query("SELECT COUNT(*) FROM " + DatabaseManager.COUNTRIES)
    int count();

    @Query("DELETE FROM " + DatabaseManager.COUNTRIES)
    void deleteAllCountries();
}
