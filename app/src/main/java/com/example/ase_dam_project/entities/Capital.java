package com.example.ase_dam_project.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ase_dam_project.database.DatabaseManager;

@Entity(tableName = DatabaseManager.CAPITALS,
        indices = {@Index(value={"name"}, unique = true)})
public class Capital implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String name;

    public Capital(String name) {
        this.name = name;
    }

    @Ignore
    public Capital(Parcel parcel) {
        this.name = parcel.readString();
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

    public static final Creator<Capital> CREATOR = new Creator<Capital>() {
        @Override
        public Capital createFromParcel(Parcel in) {
            return new Capital(in);
        }

        @Override
        public Capital[] newArray(int size) {
            return new Capital[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
    }
}
