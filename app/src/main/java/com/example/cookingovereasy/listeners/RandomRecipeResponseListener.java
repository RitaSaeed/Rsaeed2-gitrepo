package com.example.cookingovereasy.listeners;

import android.view.View;

import com.example.cookingovereasy.Models.RandomRecipeApiResponse;
/**
 * Interface for requests for random recipes to populate the recipe search.
 */
public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
