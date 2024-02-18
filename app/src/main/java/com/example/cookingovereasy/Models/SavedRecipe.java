package com.example.cookingovereasy.Models;

import java.io.Serializable;

/**
 * A transaction object between the search fragment and cookbook fragment.
 */
public class SavedRecipe implements Serializable {
    private String category;
    private String name;
    private int id;

    /**
     * Constructor for a saved recipe to be passed between fragments.
     * @param category Category recipe is in.
     * @param name Name of recipe.
     * @param id Id of recipe.
     */
    public SavedRecipe(String category, String name, int id) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    /**
     * Returns name of the recipe.
     * @return name of recipe.
     */
    public String getName() {
        return name;
    }

    /**
     * Allows you to set the name of a recipe.
     * @param name name you want to change to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the id of a recipe
     * @return id of recipe.
     */
    public int getId() {
        return id;
    }

    /**
     * Allows you to set the id of a recipe.
     * @param id id the recipe will be set to.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the category a recipe is in.
     * @return recipe category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Allows you to set the category a recipe is in.
     * @param category the recipe will be set to.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
