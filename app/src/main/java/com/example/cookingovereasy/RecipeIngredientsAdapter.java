package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.ExtendedIngredient;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recycler view in the recipe details that holds the ingredients.
 */
public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsViewHolder>{

    Context context;
    List<ExtendedIngredient> list;

    /**
     * Constructor for the adapter that holds ingredient information in a recipe.
     * @param context the context that the ingredients UI will be applied to.
     * @param list the list of ingredients needed in a recipe.
     */
    public RecipeIngredientsAdapter(Context context, List<ExtendedIngredient> list) {

        this.context = context;
        this.list = list;
    }

    /**
     * Holds the view of the ingredients in a recipe page.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.

     * @return the view holder of ingredients.
     */
    @NonNull
    @Override
    public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecipeIngredientsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_recipe_ingredients, parent, false));
    }

    /**
     * After view holder is established, this method loads the contents of the view.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder holder, int position) {

        holder.textView_ingredients_quantity.setText(list.get(position).original);
        holder.textView_ingredients_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" +
                        list.get(position).image).into(holder.imageView_ingredients);
    }

    /**
     * Returns the size of ingredients needed for the recipe.
     * @return number of ingredients in a recipe.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}

/**
 * Class that establishes the recycler view that will hold the ingredients needed for a recipe.
 */
class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_ingredients_quantity, textView_ingredients_name;
    ImageView imageView_ingredients;

    public RecipeIngredientsViewHolder(@NonNull View itemView) {

        super(itemView);

        textView_ingredients_quantity = itemView.findViewById(R.id.textView_ingredients_quantity);
        imageView_ingredients = itemView.findViewById(R.id.imageView_ingredients);
    }
}
