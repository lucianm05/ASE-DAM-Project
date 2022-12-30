package com.example.ase_dam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.utils.Constants;
import com.example.ase_dam_project.utils.Validations;
import com.example.ase_dam_project.utils.ViewManipulation;
import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {
    private TextInputEditText tietCountryName;
    private TextInputEditText tietPopulation;
    private TextInputEditText tietFlagUrl;
    private TextInputEditText tietCofUrl;
    private TextInputEditText tietCapitalName;
    private TextInputEditText tietCca3;
    private Spinner spnContinent;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initComponents();

        intent = getIntent();
    }

    private void initComponents() {
        tietCountryName = findViewById(R.id.add_tiet_country_name);
        tietPopulation = findViewById(R.id.add_tiet_population);
        tietFlagUrl = findViewById(R.id.add_tiet_flag_url);
        tietCofUrl = findViewById(R.id.add_tiet_cof_url);
        tietCapitalName = findViewById(R.id.add_tiet_capital_name);
        tietCca3 = findViewById(R.id.add_tiet_cca3);
        spnContinent = findViewById(R.id.add_spn_continent);

        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.continents,
                android.R.layout.simple_spinner_item
                );
        spnContinent.setAdapter(spnAdapter);

        btnSave = findViewById(R.id.add_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Country country = getCountryFromForm();
                Capital capital = getCapitalFromForm();

                if(country == null || capital == null) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.invalid_form),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                intent.putExtra(Constants.COUNTRY_KEY, country);
                intent.putExtra(Constants.CAPITAL_KEY, capital);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private Country getCountryFromForm() {
        String countryName = ViewManipulation.getTietString(tietCountryName);
        long population = ViewManipulation.getTietLong(tietPopulation);
        String flagUrl = ViewManipulation.getTietString(tietFlagUrl);
        String cofUrl = ViewManipulation.getTietString(tietCofUrl);
        String cca3 = ViewManipulation.getTietString(tietCca3);
        String continentName = spnContinent.getSelectedItem().toString();

        if(Validations.isValidString(countryName) &&
                Validations.isValidString(flagUrl) &&
                Validations.isValidString(cofUrl) &&
                Validations.isValidString(cca3) &&
                Validations.isValidString(continentName) &&
                population > 0) {
            return new Country(countryName, population, flagUrl, cofUrl, continentName, cca3);
        }

        return null;
    }

    private Capital getCapitalFromForm() {
        String capitalName = ViewManipulation.getTietString(tietCapitalName);

        if(Validations.isValidString(capitalName)) {
            return new Capital(capitalName);
        }

        return null;
    }
}