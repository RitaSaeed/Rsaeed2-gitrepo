package com.example.cookingovereasy.listeners;

import com.example.cookingovereasy.Models.InstructionsResponse;

import java.util.List;
/**
 * Interface for managing the responses with saved recipes.
 */
public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
