package com.example.ase_dam_project.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {
    private String name;
    private String capitalCity;
    private long population;
    private String flagUrl;
    private String cofUrl;
    private String continentName;
    private String cca3;
    private String description;

    public Country(String name,
                   String capitalCity,
                   long population,
                   String flagUrl,
                   String cofUrl,
                   String continentName,
                   String cca3) {
        this.name = name;
        this.capitalCity = capitalCity;
        this.population = population;
        this.flagUrl = flagUrl;
        this.cofUrl = cofUrl;
        this.continentName = continentName;
        this.cca3 = cca3;
    }

    public Country(Parcel parcel) {
        this.name = parcel.readString();
        this.capitalCity = parcel.readString();
        this.population = parcel.readLong();
        this.flagUrl = parcel.readString();
        this.cofUrl = parcel.readString();
        this.continentName = parcel.readString();
        this.cca3 = parcel.readString();
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
        parcel.writeString(cofUrl);
        parcel.writeString(continentName);
        parcel.writeString(cca3);
    }
}
