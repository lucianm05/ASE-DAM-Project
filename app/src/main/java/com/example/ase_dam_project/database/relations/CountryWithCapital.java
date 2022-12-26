package com.example.ase_dam_project.database.relations;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;

public class CountryWithCapital implements Parcelable {
    @Embedded
    private Country country;
    @Relation(parentColumn = "capitalId", entityColumn = "id")
    private Capital capital;

    public CountryWithCapital(Country country, Capital capital) {
        this.country = country;
        this.capital = capital;
    }

    public CountryWithCapital(Parcel in) {
        country = in.readParcelable(Country.class.getClassLoader());
        capital = in.readParcelable(Capital.class.getClassLoader());
    }

    public static final Creator<CountryWithCapital> CREATOR = new Creator<CountryWithCapital>() {
        @Override
        public CountryWithCapital createFromParcel(Parcel in) {
            return new CountryWithCapital(in);
        }

        @Override
        public CountryWithCapital[] newArray(int size) {
            return new CountryWithCapital[size];
        }
    };

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Capital getCapital() {
        return capital;
    }

    public void setCapital(Capital capital) {
        this.capital = capital;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.country, flags);
        parcel.writeParcelable(this.capital, flags);
    }
}
