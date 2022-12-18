package com.example.ase_dam_project.utils;

public class Validations {
    public static Boolean isStringValid(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
