package com.example.ase_dam_project.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ase_dam_project.MainActivity;
import com.example.ase_dam_project.R;
import com.example.ase_dam_project.database.relations.CountryWithCapital;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsFragment extends Fragment {

    public MapsFragment() {}

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng coords) {
                    MainActivity mainActivity = (MainActivity) getContext();

                    if(mainActivity == null) return;

                    Geocoder geocoder = new Geocoder(mainActivity, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(coords.latitude, coords.longitude, 1);

                        if(addresses.size() > 0) {
                            String countryName = addresses.get(0).getCountryName();

                            if(countryName != null) {
                                ArrayList<CountryWithCapital> countriesWithCapital = mainActivity.getCountriesWithCapital();

                                for(int i = 0; i < countriesWithCapital.size(); i++) {
                                    CountryWithCapital countryWithCapital = countriesWithCapital.get(i);

                                    if(Objects.equals(countryWithCapital.getCountry().getName(), countryName)) {
                                        mainActivity.setCurrentFragment(CountryFragment.newInstance(countryWithCapital));
                                        mainActivity.openFragment();
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}