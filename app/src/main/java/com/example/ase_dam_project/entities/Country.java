package com.example.ase_dam_project.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ase_dam_project.database.DatabaseManager;

@Entity(tableName = DatabaseManager.COUNTRIES,
        indices = {@Index(value={"name"}, unique = true)},
        foreignKeys = {@ForeignKey(entity = Capital.class, parentColumns = "id", childColumns = "capitalId")} )
public class Country implements Parcelable, Comparable<Country> {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private long population;
    @ColumnInfo
    private String flagUrl;
    @ColumnInfo
    private String cofUrl;
    @ColumnInfo
    private String continentName;
    @ColumnInfo
    private String cca3;
    @ColumnInfo
    private long capitalId;
    @Ignore
    private String description;
    @Ignore
    private Capital capital;

    public Country(String name,
                   long population,
                   String flagUrl,
                   String cofUrl,
                   String continentName,
                   String cca3) {
        this.name = name;
        this.population = population;
        this.flagUrl = flagUrl;
        this.cofUrl = cofUrl;
        this.continentName = continentName;
        this.cca3 = cca3;
    }

    @Ignore
    public Country(String name,
                   Capital capital,
                   long population,
                   String flagUrl,
                   String cofUrl,
                   String continentName,
                   String cca3) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.flagUrl = flagUrl;
        this.cofUrl = cofUrl;
        this.continentName = continentName;
        this.cca3 = cca3;
    }

    @Ignore
    public Country(Parcel parcel) {
        this.name = parcel.readString();
        this.capital = (Capital) parcel.readParcelable(Capital.class.getClassLoader());
        this.population = parcel.readLong();
        this.flagUrl = parcel.readString();
        this.cofUrl = parcel.readString();
        this.continentName = parcel.readString();
        this.cca3 = parcel.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Capital getCapital() {
        return capital;
    }

    public void setCapital(Capital capital) {
        this.capital = capital;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCofUrl() {
        return cofUrl;
    }

    public void setCofUrl(String cofUrl) {
        this.cofUrl = cofUrl;
    }

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(long capitalId) {
        this.capitalId = capitalId;
    }

    public static Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel parcel) {
            return new Country(parcel);
        }

        @Override
        public Country[] newArray(int i) {
            return new Country[i];
        }
    };

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeParcelable(capital, flags);
        parcel.writeLong(population);
        parcel.writeString(flagUrl);
        parcel.writeString(cofUrl);
        parcel.writeString(continentName);
        parcel.writeString(cca3);
        parcel.writeLong(capitalId);
    }

    @Override
    public int compareTo(Country country) {
        return this.getName().compareTo(country.getName());
    }
}
