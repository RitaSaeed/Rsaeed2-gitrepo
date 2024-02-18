package com.example.cookingovereasy;

import java.io.Serializable;

public class RecipeStep implements Serializable {
    String name;

    /**
     * Creates an instance of the ingredient with a name.
     * @param name
     */
    public RecipeStep(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the ingredient as a string.
     * @return name of ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of an ingredient to a specified string.
     * @param name the new name for the ingredient.
     */
    public void setName(String name) {
        this.name = name;
    }
}
