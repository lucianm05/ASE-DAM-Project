package com.example.ase_dam_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.example.ase_dam_project.database.services.CountryWithCapitalService;
import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;
import com.example.ase_dam_project.entities.Filters;
import com.example.ase_dam_project.fragments.CountriesFragment;
import com.example.ase_dam_project.fragments.CountryFragment;
import com.example.ase_dam_project.fragments.MapsFragment;
import com.example.ase_dam_project.network.Callback;
import com.example.ase_dam_project.utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CountryWithCapital> countriesWithCapital = new ArrayList<>();
    private Map<String, Integer> continentsCount;
    private CountryWithCapital currentCountry;
    private CountryWithCapital randomCountry;
    private Filters filters = new Filters();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;

    private CountryWithCapitalService countryWithCapitalService;

    private ActivityResultLauncher<Intent> addActivityLauncher;
    private ActivityResultLauncher<Intent> chartActivityLauncher;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.countryWithCapitalService = new CountryWithCapitalService(getApplicationContext());
        this.addActivityLauncher = registerAddActivityLauncher();
        this.chartActivityLauncher = registerChartActivityLauncher();

        initComponents();
        configNavigation();
        fetchCountries();
        getRandomCountry();
        this.countryWithCapitalService.getContinentsCount(getContinentCountCallback());
    }

    @Override
    public void onBackPressed() {
        if(currentFragment.getClass().equals(CountryFragment.class)) {
            setCountriesFragment();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_new_country) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            addActivityLauncher.launch(intent);
        }

        if(item.getItemId() == R.id.menu_continents_count) {
            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
            intent.putExtra(Constants.CONTINENTS_COUNT_KEY, (Serializable) continentsCount);
            chartActivityLauncher.launch(intent);
        }

        return false;
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

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public CountryWithCapital getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(CountryWithCapital currentCountry) {
        this.currentCountry = currentCountry;
    }

    private void setCountriesFragment() {
        setCurrentFragment(CountriesFragment.newInstance(countriesWithCapital));
        openFragment();
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
                    setCountriesFragment();
                }

                if(item.getItemId() == R.id.nav_view_map) {
                    setCurrentFragment(MapsFragment.newInstance());
                    openFragment();
                }

//                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
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
        setCurrentFragment(CountryFragment.newInstance(randomCountry));
        openFragment();
    }

    public void fetchCountries() {
        this.countryWithCapitalService.getAll(this.filters, getAllCountriesCallback());
    }

    private void getRandomCountry() {
        if(randomCountry != null) {
            setRandomCountryFragment();
            return;
        };

        this.countryWithCapitalService.getRandomCountryWithCapital(new Callback<CountryWithCapital>() {
            @Override
            public void runResultOnUiThread(CountryWithCapital result) {
                if(result == null) return;
                randomCountry = result;
                setRandomCountryFragment();
            }
        });
    }

    private Callback<List<CountryWithCapital>> getAllCountriesCallback() {
        return new Callback<List<CountryWithCapital>>() {
            @Override
            public void runResultOnUiThread(List<CountryWithCapital> result) {
                if(result == null) {
                    Log.i("GET ALL CALLBACK", "RESULT IS NULL");
                    return;
                }

                Log.i("GET ALL CALLBACK", String.valueOf(result.size()));

                countriesWithCapital.clear();
                countriesWithCapital.addAll(result);
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddActivityLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                getAddActivityResultCallback()
        );
    }

    private ActivityResultLauncher<Intent> registerChartActivityLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }
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

    private Callback<Map<String, Integer>> getContinentCountCallback() {
        return new Callback<Map<String, Integer>>() {
            @Override
            public void runResultOnUiThread(Map<String, Integer> result) {
                if(result == null) return;

                continentsCount = result;
            }
        };
    }

}