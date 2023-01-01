package com.example.ase_dam_project.database.daos;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.MapInfo;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;

import java.util.List;
import java.util.Map;

@Dao
public interface CountryWithCapitalDao {
    @Insert
    long insertCountry(Country country);

    @Insert
    long insertCapital(Capital capital);

    @Transaction
    @Query("SELECT * FROM " +
            DatabaseManager.COUNTRIES +
            " WHERE NAME LIKE :countryName" +
            " ORDER BY NAME")
    List<CountryWithCapital> getAll(@Nullable String countryName);

    @Transaction
    @Query("SELECT * FROM " + DatabaseManager.COUNTRIES + " WHERE ID = :countryId")
    CountryWithCapital getCountryWithCapital(long countryId);

    @Transaction
    @Query("SELECT * FROM " + DatabaseManager.COUNTRIES + " WHERE NAME = :countryName")
    CountryWithCapital getCountryWithCapitalByName(String countryName);

    @Transaction
    @Query("SELECT COUNT(*) FROM " + DatabaseManager.COUNTRIES)
    int count();

    @Transaction
    @MapInfo(keyColumn = "continentName", valueColumn = "count")
    @Query("SELECT continentName, COUNT(*) AS count FROM " + DatabaseManager.COUNTRIES + " GROUP BY CONTINENTNAME")
    Map<String, Integer> getContinentsCount();
}
