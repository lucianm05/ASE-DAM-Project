package com.example.ase_dam_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ase_dam_project.utils.Chart;
import com.example.ase_dam_project.utils.Constants;

import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, Integer> source = (Map<String, Integer>) getIntent()
                .getSerializableExtra(Constants.CONTINENTS_COUNT_KEY);
        setContentView(new Chart(getApplicationContext(), source));
    }
}