package com.example.ase_dam_project.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ase_dam_project.MainActivity;
import com.example.ase_dam_project.R;
import com.example.ase_dam_project.adapters.CountryCardAdapter;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.entities.Filters;
import com.example.ase_dam_project.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CountriesFragment extends Fragment {
    private ArrayList<CountryWithCapital> countriesWithCapital;

    private ListView lvCountries;
    private SearchView svCountries;

    public CountriesFragment() {}

    public static CountriesFragment newInstance(ArrayList<CountryWithCapital> countries) {
        CountriesFragment fragment = new CountriesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.COUNTRIES_WITH_CAPITAL_KEY, countries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.countriesWithCapital = getArguments().getParcelableArrayList(Constants.COUNTRIES_WITH_CAPITAL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents (View view) {
        lvCountries = view.findViewById(R.id.countries_lv);
        svCountries = view.findViewById(R.id.countries_sv);

        if(getContext() != null) {
            CountryCardAdapter adapter = new CountryCardAdapter(
                    getContext().getApplicationContext(),
                    R.layout.country_card,
                    this.countriesWithCapital,
                    getLayoutInflater()
                    );
            lvCountries.setAdapter(adapter);

            svCountries.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    fetchOnFiltersChange(query);
                    adapter.notifyDataSetChanged();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if(query.isEmpty()) {
                        fetchOnFiltersChange(query);
                        adapter.notifyDataSetChanged();
                    }
                    return false;
                }
            });
            svCountries.setQuery(getFiltersFromActivity().getCountryName(), false);
        }
    }

    private void fetchOnFiltersChange(String countryName) {
        MainActivity mainActivity = (MainActivity) getContext();

        if(mainActivity != null) {
            mainActivity.getFilters().setCountryName(countryName);
            mainActivity.fetchCountries();
        }
    }

    private Filters getFiltersFromActivity() {
        MainActivity mainActivity = (MainActivity) getContext();

        if(mainActivity == null) return null;

        return mainActivity.getFilters();
    }
}