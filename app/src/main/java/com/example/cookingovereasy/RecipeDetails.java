package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.cookingovereasy.Models.InstructionsResponse;
import com.example.cookingovereasy.Models.Recipe;
import com.example.cookingovereasy.Models.RecipeDetailsResponse;
import com.example.cookingovereasy.listeners.InstructionsListener;
import com.example.cookingovereasy.listeners.RecipeDetailsListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Page that shows an individual recipe's title, picture, ingredients, and steps.
 */
public class RecipeDetails extends AppCompatActivity {

    int id;
    TextView textView_recipe_name, textView_recipe_source, textView_recipe_summary;
    ImageView imageView_recipe_image;
    ImageButton recipe_details_back, recipe_details_favorite;
    RecyclerView recycler_recipe_ingredients, recycler_recipe_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    RecipeIngredientsAdapter recipeIngredientsAdapter;
    InstructionsAdapter instructionsAdapter;
    ArrayList<Category> categories;

    /**
     * Code that is done on creation of the activity.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.
     *                           </i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);
        findViews();
        // on click listener for the back button, goes back to search fragment
        recipe_details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // capture category arraylist from sent intent
        categories = (ArrayList<Category>) getIntent().getSerializableExtra("categories");
        // on click listener for the favorite button
        recipe_details_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu p = new PopupMenu(RecipeDetails.this, v);
                for (Category c : categories) {
                    p.getMenu().add(c.getName());
                }
                p.show();
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(RecipeDetails.this,
                                textView_recipe_name.getText() + " added to " + item.toString(),
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("category", item.toString());
                        intent.putExtra("id", id);
                        intent.putExtra("name", textView_recipe_name.getText());
                        setResult(RESULT_OK, intent);
                        finish();
                        return true;
                    }
                });
            }
        });

        // capture id from sent intent
        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();

    }

    /**
     * Establishes the views from the corresponding xml file.
     */
    private void findViews() {

        textView_recipe_name = findViewById(R.id.textView_recipe_name);
        imageView_recipe_image = findViewById(R.id.imageView_recipe_image);
        recycler_recipe_ingredients = findViewById(R.id.recycler_recipe_ingredients);
        recycler_recipe_instructions = findViewById(R.id.recycler_recipe_instructions);
        recipe_details_back = findViewById(R.id.recipe_details_back);
        recipe_details_favorite = findViewById(R.id.recipe_details_favorite);
    }

    /**
     * Listener for the recipe title, image, and ingredients.
     */
    private final RecipeDetailsListener listener = new RecipeDetailsListener() {

        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_recipe_name.setText(response.title);
            Picasso.get().load(response.image).into(imageView_recipe_image);

            recycler_recipe_ingredients.setLayoutManager(new
                    LinearLayoutManager(RecipeDetails.this,
                    LinearLayoutManager.VERTICAL, false));
            recipeIngredientsAdapter = new RecipeIngredientsAdapter(RecipeDetails.this,
                    response.extendedIngredients);
            recycler_recipe_ingredients.setAdapter(recipeIngredientsAdapter);
            recycler_recipe_ingredients.setHasFixedSize(true);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Listener for the analyzed instructions of the recipe.
     */
    private final InstructionsListener instructionsListener = new InstructionsListener() {

        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_recipe_instructions.setHasFixedSize(true);
            recycler_recipe_instructions.setLayoutManager(new
                    LinearLayoutManager(RecipeDetails.this,
                    LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetails.this, response);
            recycler_recipe_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}