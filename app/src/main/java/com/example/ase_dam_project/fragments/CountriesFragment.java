package com.example.ase_dam_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ase_dam_project.R;
import com.example.ase_dam_project.adapters.CountryCardAdapter;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CountriesFragment extends Fragment {
    private ArrayList<Country> countries;

    private ListView lvCountries;

    public CountriesFragment() {}

    public static CountriesFragment newInstance(ArrayList<Country> countries) {
        CountriesFragment fragment = new CountriesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.COUNTRIES_KEY, countries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.countries = getArguments().getParcelableArrayList(Constants.COUNTRIES_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents (View view) {
        lvCountries = view.findViewById(R.id.countries_lv);

        if(getContext() != null) {
            CountryCardAdapter adapter = new CountryCardAdapter(
                    getContext().getApplicationContext(),
                    R.layout.country_card,
                    this.countries,
                    getLayoutInflater()
                    );
            lvCountries.setAdapter(adapter);
        }
    }
}