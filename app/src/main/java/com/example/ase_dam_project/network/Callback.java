package com.example.ase_dam_project.network;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
