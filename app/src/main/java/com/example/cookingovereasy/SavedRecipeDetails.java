package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.InstructionsResponse;
import com.example.cookingovereasy.Models.RecipeDetailsResponse;
import com.example.cookingovereasy.listeners.InstructionsListener;
import com.example.cookingovereasy.listeners.RecipeDetailsListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Class that will save and allow access to the data required for a saved recipe.
 */
public class SavedRecipeDetails extends AppCompatActivity {

    int id;
    TextView textView_recipe_name;
    ImageView imageView_recipe_image;
    ImageButton recipe_details_back;
    RecyclerView recycler_recipe_ingredients, recycler_recipe_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    RecipeIngredientsAdapter recipeIngredientsAdapter;
    InstructionsAdapter instructionsAdapter;

    /**
     * Creates the activity where a saved recipe will be displayed.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.
     *                           </i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_recipe_details);
        findViews();
        recipe_details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();

    }

    /**
     * Finds and displays the information that will populate the recycler views for a saves recipe.
     */
    private void findViews() {

        textView_recipe_name = findViewById(R.id.textView_recipe_name);
        imageView_recipe_image = findViewById(R.id.imageView_recipe_image);
        recycler_recipe_ingredients = findViewById(R.id.recycler_recipe_ingredients);
        recycler_recipe_instructions = findViewById(R.id.recycler_recipe_instructions);
        recipe_details_back = findViewById(R.id.recipe_details_back);
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
                    LinearLayoutManager(SavedRecipeDetails.this,
                    LinearLayoutManager.VERTICAL, false));
            recipeIngredientsAdapter = new RecipeIngredientsAdapter(SavedRecipeDetails.this,
                    response.extendedIngredients);
            recycler_recipe_ingredients.setAdapter(recipeIngredientsAdapter);
            recycler_recipe_ingredients.setHasFixedSize(true);
        }

        /**
         * Toast message if there is an error.
         * @param message message that will be displayed.
         */
        @Override
        public void didError(String message) {

            Toast.makeText(SavedRecipeDetails.this, message, Toast.LENGTH_SHORT).show();
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
                    LinearLayoutManager(SavedRecipeDetails.this,
                    LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(SavedRecipeDetails.this, response);
            recycler_recipe_instructions.setAdapter(instructionsAdapter);
        }

        /**
         * Toast message if there is an error.
         * @param message that will be displayed if there is an error.
         */
        @Override
        public void didError(String message) {

            Toast.makeText(SavedRecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}