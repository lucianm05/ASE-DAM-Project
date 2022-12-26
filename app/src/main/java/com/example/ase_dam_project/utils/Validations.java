package com.example.ase_dam_project.utils;

import com.example.ase_dam_project.entities.Country;

public class Validations {
    public static Boolean isStringValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static Boolean isValidCountry(Country country) {
        return country != null &&
                country.getCapital() != null &&
                country.getName() != null &&
                country.getPopulation() > 0 &&
                country.getFlagUrl() != null &&
                country.getCofUrl() != null &&
                country.getContinentName() != null &&
                country.getCca3() != null;
    }
}
