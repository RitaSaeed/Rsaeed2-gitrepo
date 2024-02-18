package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.InstructionsResponse;
import com.example.cookingovereasy.Models.RecipeDetailsResponse;
import com.example.cookingovereasy.listeners.InstructionsListener;
import com.example.cookingovereasy.listeners.RecipeDetailsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyRecipeDetails extends AppCompatActivity {
    MyRecipe recipe;
    TextView textView_recipe_name;
    ImageButton myrecipe_details_back;
    RecyclerView recycler_recipe_ingredients, recycler_recipe_instructions;
    MyIngredientAdapter recipeIngredientsAdapter;
    RecipeStepAdapter instructionsAdapter;

    /**
     * Code that is done on creation of the activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe_details);
        findViews();
        // capture id from sent intent
        recipe = (MyRecipe) getIntent().getSerializableExtra("recipe");

        textView_recipe_name.setText(recipe.title);

        //set up recycler view for ingredients:
        recycler_recipe_ingredients.setLayoutManager(new
                LinearLayoutManager(MyRecipeDetails.this,
                LinearLayoutManager.VERTICAL, false));
        recipeIngredientsAdapter = new MyIngredientAdapter(recipe.myIngredients);
        recycler_recipe_ingredients.setAdapter(recipeIngredientsAdapter);
        recycler_recipe_ingredients.setHasFixedSize(true);

        //set up recycler view for recipe steps:
        recycler_recipe_instructions.setLayoutManager(new
                LinearLayoutManager(MyRecipeDetails.this,
                LinearLayoutManager.VERTICAL, false));
        instructionsAdapter = new RecipeStepAdapter(recipe.myInstructions);
        recycler_recipe_instructions.setAdapter(instructionsAdapter);
        recycler_recipe_instructions.setHasFixedSize(true);

        // on click listener for the back button, goes back to search fragment
        myrecipe_details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Establishes the views from the corresponding xml file.
     */
    private void findViews() {
        textView_recipe_name = findViewById(R.id.textView_myrecipe_name);
        recycler_recipe_ingredients = findViewById(R.id.recycler_myrecipe_ingredients);
        recycler_recipe_instructions = findViewById(R.id.recycler_myrecipe_instructions);
        myrecipe_details_back = findViewById(R.id.myrecipe_details_back);
    }


}