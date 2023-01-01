package com.example.ase_dam_project.utils;

import com.example.ase_dam_project.entities.Capital;
import com.example.ase_dam_project.entities.Country;

public class Validations {
    public static Boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static Boolean isValidCountry(Country country) {
        return country != null &&
                country.getName() != null &&
                country.getPopulation() > 0 &&
                country.getFlagUrl() != null &&
                country.getCofUrl() != null &&
                country.getContinentName() != null &&
                country.getCca3() != null;
    }

    public static Boolean isValidCapital(Capital capital) {
        return capital != null &&
                capital.getName() != null;
    }
}
