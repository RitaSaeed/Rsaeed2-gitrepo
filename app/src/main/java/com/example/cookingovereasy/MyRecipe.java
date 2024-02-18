package com.example.cookingovereasy;

import java.io.Serializable;
import java.util.ArrayList;

public class MyRecipe implements Serializable {

    String title;
    ArrayList<Ingredient> myIngredients;
    ArrayList<RecipeStep> myInstructions;

    public MyRecipe(String title, ArrayList<Ingredient> myIngredients, ArrayList<RecipeStep> myInstructions){
        this.title = title;
        this.myIngredients = myIngredients;
        this.myInstructions = myInstructions;
    }

    public String toString(){
        String str = title + "\n";
        for(Ingredient ingredient : myIngredients){
            str += ingredient.getName() + "\n";
        }
        for(RecipeStep step : myInstructions){
            str += step.getName() + "\n";
        }
        return str;
    }


}
