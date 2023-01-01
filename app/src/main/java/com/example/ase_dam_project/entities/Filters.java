package com.example.ase_dam_project.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Filters implements Parcelable {
    private String countryName;
    private String continentName;

    public Filters() {
        this.countryName = "";
        this.continentName = "";
    }

    public Filters(Parcel parcel) {
        this.countryName = parcel.readString();
        this.continentName = parcel.readString();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public static final Creator<Filters> CREATOR = new Creator<Filters>() {
        @Override
        public Filters createFromParcel(Parcel in) {
            return new Filters(in);
        }

        @Override
        public Filters[] newArray(int size) {
            return new Filters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.countryName);
        parcel.writeString(this.continentName);
    }
}
