package com.example.cookingovereasy;

import java.io.Serializable;

/**
 * Represents an ingredient in the grocery list.
 */
public class Ingredient implements Serializable {
    String name;
    boolean isSelected;

    /**
     * Creates an instance of the ingredient with a name.
     * @param name
     */
    public Ingredient(String name) {
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

    /**
     * Checks whether an ingredient is selected or not.
     * @return true if selected, false otherwise
     */
    public boolean getSelected() {
        return isSelected;
    }

    /**
     * Changes the state of the ingredient to the input state.
     * @param selected new state of ingredient
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
