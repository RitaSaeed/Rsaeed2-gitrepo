package com.example.cookingovereasy.Models;

import java.util.ArrayList;

/**
 * Extended information regarding ingredients in a recipe on the API.
 */
public class ExtendedIngredient {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalName;
    public double amount;
    public String unit;
    public ArrayList<String> meta;
    public Measures measures;
}
