package com.example.cookingovereasy;

/**
 * Represents a food object, for use in the search activity.
 */
public class Food {

    String heading;
    int titleImage;

    /**
     * Creates an instance of a food object.
     * @param heading the name of the recipe
     * @param titleImage the image to accompany the recipe
     */
    public Food(String heading, int titleImage) {

        this.heading = heading;
        this.titleImage = titleImage;
    }

    /**
     * Returns the name of the recipe as a string
     * @return name of the recipe
     */
    public String getHeading() {
        return heading;
    }

}
