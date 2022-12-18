package com.example.ase_dam_project.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {
    private String name;
    private String capitalCity;
    private long population;
    private String flagUrl;
    private String continentName;
    private String cca3;

    public Country(String name, String capitalCity, long population, String flagUrl, String continentName, String cca3) {
        this.name = name;
        this.capitalCity = capitalCity;
        this.population = population;
        this.flagUrl = flagUrl;
        this.continentName = continentName;
        this.cca3 = cca3;
    }

    public Country(Parcel parcel) {
        this.name = parcel.readString();
        this.capitalCity = parcel.readString();
        this.population = parcel.readLong();
        this.flagUrl = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
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
        parcel.writeString(capitalCity);
        parcel.writeLong(population);
        parcel.writeString(flagUrl);
    }
}
