package com.example.ase_dam_project.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;

import java.util.List;

@Dao
public interface CountryWithCapitalDao {
    @Insert
    long insertCountry(Country country);

    @Insert
    long insertCapital(Capital capital);

    @Transaction
    @Query("SELECT * FROM " + DatabaseManager.COUNTRIES + " ORDER BY NAME")
    List<CountryWithCapital> getAll();

    @Transaction
    @Query("SELECT * FROM " + DatabaseManager.COUNTRIES + " WHERE id = :countryId")
    CountryWithCapital getCountryWithCapital(long countryId);
}
