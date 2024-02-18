package com.example.cookingovereasy.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the response for an ingredient search.
 */
public class IngredientResponse {
    public ArrayList<Result> results;
    public int offset;
    public int number;
    public int totalResults;
}
