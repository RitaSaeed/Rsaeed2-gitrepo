package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.RecipeDetailsResponse;
/**
 * Interface for retrieving recipe details from the API
 */
public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
