package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.IngredientResponse;

/**
 * Interface for managing the responses from the ingredient response class
 */
public interface IngredientResponseListener {
    void didFetch(IngredientResponse response, String message);
    void didError(String message);
}
