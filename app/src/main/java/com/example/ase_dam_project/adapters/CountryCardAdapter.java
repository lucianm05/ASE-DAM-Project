package com.example.ase_dam_project.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ase_dam_project.MainActivity;
import com.example.ase_dam_project.R;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.fragments.CountryFragment;
import com.example.ase_dam_project.utils.Validations;
import com.example.ase_dam_project.utils.ViewManipulation;

import java.util.ArrayList;
import java.util.List;

public class CountryCardAdapter extends ArrayAdapter<CountryWithCapital> {
    private Context context;
    private int resource;
    private List<CountryWithCapital> countriesWithCapital;
    private LayoutInflater inflater;

    public CountryCardAdapter(@NonNull Context context,
                              int resource,
                              @NonNull List<CountryWithCapital> objects,
                              LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.countriesWithCapital = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        CountryWithCapital countryWithCapital = this.countriesWithCapital.get(position);
        Country country = countryWithCapital.getCountry();
        Capital capital = countryWithCapital.getCapital();

        if(country != null) {
            ViewManipulation.addTextViewValue(view, R.id.country_card_name, country.getName());
            ViewManipulation.addTextViewValue(view, R.id.country_card_capital, capital.getName());
            ViewManipulation.addTextViewValue(view, R.id.country_card_continent, country.getContinentName());
            ViewManipulation.addTextViewValue(view, R.id.country_card_population, getContext().getString(
                    R.string.population_count,
                    country.getPopulation()));
            ViewManipulation.addImageViewUrl(view, R.id.country_card_flag, country.getFlagUrl());

            Button viewMoreBtn = view.findViewById(R.id.country_card_view_more_btn);
            viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity activity = (MainActivity) view.getContext();
                    activity.setCurrentFragment(CountryFragment.newInstance(countryWithCapital));
                    activity.openFragment();
                }
            });
        }

        return view;
    }


}
