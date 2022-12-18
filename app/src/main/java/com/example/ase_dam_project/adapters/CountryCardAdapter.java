package com.example.ase_dam_project.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.ase_dam_project.R;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.utils.Validations;

import java.util.List;

public class CountryCardAdapter extends ArrayAdapter<Country> {
    private Context context;
    private int resource;
    private List<Country> countries;
    private LayoutInflater inflater;

    public CountryCardAdapter(@NonNull Context context,
                              int resource,
                              @NonNull List<Country> objects,
                              LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.countries = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Country country = countries.get(position);

        if(country != null) {
            addTextViewValue(view, R.id.country_card_name, country.getName());
            addTextViewValue(view, R.id.country_card_capital, country.getCapitalCity());
            addTextViewValue(view, R.id.country_card_continent, country.getContinentName());
            addTextViewValue(view, R.id.country_card_population, getContext().getString(
                    R.string.population_count,
                    country.getPopulation()));
            addImageViewUrl(view, R.id.country_card_flag, country.getFlagUrl());
        }

        return view;
    }

    private void addTextViewValue(View view, int textViewId, String value) {
        TextView textView = view.findViewById(textViewId);

        if(Validations.isStringValid(value)) {
            textView.setText(value);
        } else {
            textView.setText(R.string.dash);
        }
    }

    private void addImageViewUrl(View view, int imageViewId, String url) {
        ImageView imageView = view.findViewById(imageViewId);

        if(Validations.isStringValid(url)) {
            Glide.with(view).load(url).into(imageView);
        }
    }
}
