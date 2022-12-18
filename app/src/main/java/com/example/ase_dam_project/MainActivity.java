package com.example.ase_dam_project;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.fragments.CountriesFragment;
import com.example.ase_dam_project.utils.JsonParser;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ase_dam_project.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Country> countries = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        configNavigation();
        initCountries();
    }

    private void initComponents() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_all_countries) {
                    currentFragment = CountriesFragment.newInstance(countries);
                }

                openFragment();

                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initCountries() {
        try {
            JSONArray countriesJSONArray = new JSONArray(JsonParser.readFromJson(
                    this,
                    "countries.json"));
            for(int i = 0; i < countriesJSONArray.length(); i++) {
                JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);
                Country country = JsonParser.getCountry(countryJSONObject);
                if(country != null) this.countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }
}