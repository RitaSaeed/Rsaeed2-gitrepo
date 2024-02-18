package com.example.cookingovereasy;

/**
 * Object for the ingredients as they are when they are being searched.
 */
public class SearchIngredient {

    private String ingredientName;

    /**
     * Constructor for an ingredient in the search view.
     * @param name of the ingredient searched.
     */
    public SearchIngredient(String name) {
        this.ingredientName = name;
    }

    /**
     * Returns the name of the ingredient in an ingredient search.
     * @return the name of an ingredient.
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Sets the name for an ingredient in an ingredient search.
     * @param ingredientName
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
