package com.example.cookingovereasy.Models;

import java.util.ArrayList;

/**
 * Object for a step in a recipe.
 */
public class Step {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Equipment> equipment;
    public Length length;
}
