package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;

/**
 * Brings up an activity that is a category created in the cookbook as well as the saved recipes
 * that go with it.
 */
public class Subcategory extends AppCompatActivity {

    TextView categoryNameHeader;
    ImageView backToCategories;
    RecyclerView categoryItems;
    ArrayList<SavedRecipe> recipes;
    SavedRecipesAdapter adapter;

    /**
     * Creates the view for the activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.
     *                           </i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subcategory);
        getViews();
        recipes = (ArrayList<SavedRecipe>) getIntent().getSerializableExtra("categoryItems");
        categoryNameHeader.setText(getIntent().getStringExtra("category"));
        backToCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        categoryItems.setLayoutManager(new LinearLayoutManager(Subcategory.this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new SavedRecipesAdapter(Subcategory.this, recipes, recipeClickListener);
        categoryItems.setAdapter(adapter);
        categoryItems.setHasFixedSize(true);

    }

    /**
     * Gets the  view specific to the category that is selected.
     */
    private void getViews() {

        categoryNameHeader = findViewById(R.id.categoryNameHeader);
        backToCategories = findViewById(R.id.backToCategories);
        categoryItems = findViewById(R.id.categoryItems);
    }

    /**
     * Opens the recipe page of a recipe that is clicked inside a category.
     */
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {

        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(Subcategory.this, SavedRecipeDetails.class)
                    .putExtra("id", id));
        }
    };
}