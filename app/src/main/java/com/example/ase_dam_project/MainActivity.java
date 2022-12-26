package com.example.ase_dam_project;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.ase_dam_project.database.DatabaseManager;
import com.example.ase_dam_project.database.daos.CountryWithCapitalDao;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.database.services.CapitalService;
import com.example.ase_dam_project.database.services.CountryService;
import com.example.ase_dam_project.database.services.CountryWithCapitalService;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.fragments.CountriesFragment;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
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
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CountryWithCapital> countriesWithCapital = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment currentFragment;

    private CountryService countryService;
    private CapitalService capitalService;
    private CountryWithCapitalService countryWithCapitalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.countryService = new CountryService(getApplicationContext());
        this.capitalService = new CapitalService(getApplicationContext());
        this.countryWithCapitalService = new CountryWithCapitalService(getApplicationContext());

        initComponents();
        configNavigation();
        this.countryWithCapitalService.getAll(getAllCountriesCallback());
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    private void initComponents() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_all_countries) {
                    currentFragment = CountriesFragment.newInstance(countriesWithCapital);
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

    public void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }

    private Callback<List<CountryWithCapital>> getAllCountriesCallback() {
        return new Callback<List<CountryWithCapital>>() {
            @Override
            public void runResultOnUiThread(List<CountryWithCapital> result) {
                if(result == null) {
                    Log.i("GET ALL RESULT", "IS NULL");
                    return;
                }

                Log.i("GET ALL RESULT", String.valueOf(result.size()));
                for(int i = 0; i < result.size(); i++) {
                    CountryWithCapital res = result.get(i);
                    Log.i("RESULT " + i, "COUNTRY " + res.getCountry() == null ? " IS NULL" : " EXISTS");
                    Log.i("RESULT " + i, "CAPITAL " + res.getCapital() == null ? " IS NULL" : " EXISTS");
                }

                countriesWithCapital.addAll(result);
            }
        };
    }
}