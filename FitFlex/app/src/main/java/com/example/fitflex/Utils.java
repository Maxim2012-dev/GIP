package com.example.fitflex;

import java.util.regex.Pattern;

public class Utils {

    public static final Pattern WACHTWOORD_PATROON =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //Minstens 1 cijfer
                    "(?=.*[a-z])" +         //Minstens 1 kleine letter
                    "(?=.*[A-Z])" +         //Minstens 1 hoofdletter
                    ".{6,}");               //Minstens 6 karakters

}
