package com.example.ase_dam_project;

import android.content.Intent;
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
import com.example.ase_dam_project.fragments.CountryFragment;
import com.example.ase_dam_project.fragments.MapsFragment;
import com.example.ase_dam_project.network.AsyncTaskRunner;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.utils.Constants;
import com.example.ase_dam_project.utils.JsonParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CountryWithCapital> countriesWithCapital = new ArrayList<>();
    private int randomInt;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fabAdd;
    private Fragment currentFragment;

    private CountryWithCapitalService countryWithCapitalService;

    private ActivityResultLauncher<Intent> addActivityLauncher;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.countryWithCapitalService = new CountryWithCapitalService(getApplicationContext());
        this.addActivityLauncher = registerAddActivityLauncher();

        initComponents();
        configNavigation();

        this.countryWithCapitalService.getAll(getAllCountriesCallback());

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Log.i("LOCATION", fusedLocationProviderClient.toString());
    }

    public ArrayList<CountryWithCapital> getCountriesWithCapital() {
        return countriesWithCapital;
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
                if(item.getItemId() == R.id.nav_homepage) {
                    setRandomCountryFragment();
                }

                if(item.getItemId() == R.id.nav_all_countries) {
                    setCurrentFragment(CountriesFragment.newInstance(countriesWithCapital));
                    openFragment();
                }

                if(item.getItemId() == R.id.nav_view_map) {
                    setCurrentFragment(MapsFragment.newInstance());
                    openFragment();
                }

                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                addActivityLauncher.launch(intent);
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

    public void setRandomCountryFragment() {
        CountryWithCapital countryWithCapital = countriesWithCapital.get(randomInt);
        setCurrentFragment(CountryFragment.newInstance(countryWithCapital));
        openFragment();
    }

    private Callback<List<CountryWithCapital>> getAllCountriesCallback() {
        return new Callback<List<CountryWithCapital>>() {
            @Override
            public void runResultOnUiThread(List<CountryWithCapital> result) {
                if(result == null) {
                    return;
                }

                countriesWithCapital.addAll(result);
                Random random = new Random();
                randomInt = random.nextInt(countriesWithCapital.size());
                setRandomCountryFragment();
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddActivityLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                getAddActivityResultCallback()
        );
    }

    private ActivityResultCallback<ActivityResult> getAddActivityResultCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() != RESULT_OK ||
                    result.getData() == null)
                    return;

                Country country = result.getData().getParcelableExtra(Constants.COUNTRY_KEY);
                Capital capital = result.getData().getParcelableExtra(Constants.CAPITAL_KEY);

                if(country == null || capital == null) {
                    return;
                }

                countryWithCapitalService.insert(country, capital, new Callback<CountryWithCapital>() {
                    @Override
                    public void runResultOnUiThread(CountryWithCapital result) {
                        if(result == null) {
                            return;
                        }

                        countriesWithCapital.add(result);
                    }
                });
            }
        };
    }
}