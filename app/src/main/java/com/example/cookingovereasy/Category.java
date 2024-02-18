package com.example.cookingovereasy;

import java.io.Serializable;

/**
 * Represents a category in the grocery list.
 */
public class Category implements Serializable {
    String name;

    /**
     * Creates an instance of the category with a name.
     * @param name
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the category as a string.
     * @return name of ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a category to a specified string.
     * @param name the new name for the ingredient.
     */
    public void setName(String name) {
        this.name = name;
    }

}
