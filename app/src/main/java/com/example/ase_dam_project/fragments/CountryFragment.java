package com.example.ase_dam_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ase_dam_project.R;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.network.HttpManager;
import com.example.ase_dam_project.utils.Constants;
import com.example.ase_dam_project.utils.ViewManipulation;

import org.json.JSONException;
import org.json.JSONObject;

public class CountryFragment extends Fragment {
    private View view;
    private Country country;

    private AsyncTaskRunner asyncTaskRunner;


    public CountryFragment() {}


    public static CountryFragment newInstance(Country country) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.COUNTRY_KEY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
           this.country = getArguments().getParcelable(Constants.COUNTRY_KEY);
           asyncTaskRunner = new AsyncTaskRunner();
           loadCountryDescription();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        this.view = view;

        if(this.country != null) {
            ViewManipulation.addTextViewValue(view, R.id.country_name, this.country.getName());
            ViewManipulation.addTextViewValue(
                    view,
                    R.id.country_capital,
                    getString(R.string.capital_city, this.country.getCapitalCity()));
            ViewManipulation.addTextViewValue(
                    view,
                    R.id.country_continent,
                    getString(R.string.located_on_the_continent, this.country.getContinentName()));
            ViewManipulation.addImageViewUrl(view, R.id.country_flag, this.country.getFlagUrl());
            ViewManipulation.addImageViewUrl(view, R.id.country_cof, this.country.getCofUrl());

        }

        return view;
    }

    private void loadCountryDescription() {
        HttpManager callable = new HttpManager(Constants.WIKIPEDIA_SUMMARY_URL + this.country.getName());
        asyncTaskRunner.executeAsync(callable, new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String countryDescription = object.getString(Constants.EXTRACT);
                    if(countryDescription != null) {
                        country.setDescription(countryDescription);
                    }
                    ViewManipulation.addTextViewValue(view, R.id.country_description, country.getDescription());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}