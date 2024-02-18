package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main page of the app, holds the fragments and the navigation bar at the bottom.
 */
public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CookBookFragment cookBookFragment = new CookBookFragment();
    CreateRecipeFragment createRecipeFragment = new CreateRecipeFragment();
    GroceryListFragment groceryListFragment = new GroceryListFragment();
    SearchFragment searchFragment = new SearchFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    ArrayList<Category> categories;
    SavedRecipe currentRecipe;
    Map<String, ArrayList<SavedRecipe>> categoryMap;
    ArrayList<MyRecipe> myCreatedRecipes;

    /**
     * Does the bulk of the work for this page, initializes the xml page objects and sets
     * their action listeners.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.
     *                           </i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage);
        categories = new ArrayList<>();
        myCreatedRecipes = new ArrayList<>();
        loadData();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navbarcontainer,cookBookFragment).commit();
        getFragmentManager().beginTransaction();

        /**
         * Switch statement that determines which fragment to load onto the page by which button
         * in the navigation bar is selected by the user.
         */
        bottomNavigationView.setOnItemSelectedListener
                (new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.cookbook:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,cookBookFragment).commit();
                        return true;
                    case R.id.addrecipe:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,createRecipeFragment).commit();
                        return true;
                    case R.id.grocerylist:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,groceryListFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction()
                                .remove(cookBookFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(searchFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(groceryListFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .remove(createRecipeFragment).commit();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,settingsFragment).commit();
                        return true;
                    case R.id.search:
                        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.navbarcontainer,searchFragment).commit();

                        return true;
                }

                return false;
            }
        });
    }

    /**
     * Sets the categories in the cookbook.
     * @param newCategories ArrayList of updated categories.
     */
    public void setCategories(ArrayList<Category> newCategories) {
        categories = newCategories;
    }

    /**
     * Retrives the current categories in the cookbook.
     * @return an arraylist of cookbook categories.
     */
    public ArrayList<Category> retrieveCategories() {
        return this.categories;
    }

    /**
     * Adds a saved recipe to a hashmap of saved recipes.
     * @param recipe the recipe that will be saved to the map.
     */
    public void addSavedRecipe(SavedRecipe recipe) {
        categoryMap.get(recipe.getCategory()).add(recipe);
        saveData();
    }

    /**
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Add a cookbook category to a hashmap of recipes.
     * @param categoryName
     */
    public void addMapCategory(String categoryName) {

        categoryMap.put(categoryName, new ArrayList<>());
    }

    /**
     * Gets all the recipes saved to a category from the hashmap.
     * @param categoryName the name of the category that is being retreived.
     * @return An Arraylist of recipes saved to the category in the hashmap.
     */
    public ArrayList<SavedRecipe> retrieveCategoryItems(String categoryName) {

        return categoryMap.get(categoryName);
    }
    
    /**
     * Saves data pertaining to the category hashmap.
     */
    public void addCustomRecipe(MyRecipe recipe) {
        myCreatedRecipes.add(recipe);
        saveData();
    }

    public ArrayList<MyRecipe> getCustomRecipes() {
        loadData();
        return myCreatedRecipes;
    }

    public void saveData(){

        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categoryMap);
        editor.putString("ListCategories", json);
        json = gson.toJson(myCreatedRecipes);
        editor.putString("CreatedRecipes", json);
        editor.apply();
    }

    /**
     * Loads data pertaining to the category hashmap.
     */
    public void loadData(){

        SharedPreferences sp = getSharedPreferences("preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("ListCategories", null);
        Type type = new TypeToken<Map<String, ArrayList<SavedRecipe>>>() {}.getType();
        categoryMap = gson.fromJson(json, type);

        if (categoryMap == null) {
            categoryMap = new HashMap<>();
        }

        String jsonCustom = sp.getString("CreatedRecipes", null);
        Type customType = new TypeToken<ArrayList<MyRecipe>>() {}.getType();
        myCreatedRecipes = gson.fromJson(jsonCustom, customType);

        if (myCreatedRecipes == null) {
            myCreatedRecipes = new ArrayList<>();
        }
    }

}