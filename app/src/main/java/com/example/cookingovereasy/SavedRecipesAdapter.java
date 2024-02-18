package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the view of saved recipes inside a category in the cookbook.
 */
public class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesViewHolder>{

    Context context;
    ArrayList<SavedRecipe> list;
    RecipeClickListener listener;

    /**
     * Constructor for the adapter for the saved recipes view holder.
     * @param context
     * @param list list of saved recipes.
     * @param listener
     */
    public SavedRecipesAdapter(Context context, ArrayList<SavedRecipe> list,
                               RecipeClickListener listener) {

        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    /**
     * Creates the view holder of saved recipes.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public SavedRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SavedRecipesViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.cookbook_recipe_item, parent, false));
    }

    /**
     * Populates the view holder after it is created.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SavedRecipesViewHolder holder, int position) {

        holder.savedRecipeName.setText(list.get(position).getName());
        holder.cookbook_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition())
                        .getId()));
            }
        });
    }

    /**
     * Returns number of saved recipes in a category.
     * @return number of saved recipes in a category.
     */
    @Override
    public int getItemCount() {
        return list.size(); // list is null here
    }
}

/**
 * The view holder for the saved recipes in a cookbook category.
 */
class SavedRecipesViewHolder extends RecyclerView.ViewHolder {

    TextView savedRecipeName;
    CardView cookbook_list_container;

    /**
     * Constructor for the recycler view of saved recipes.
     * @param itemView the view that will hold the items in the recycler view.
     */
    public SavedRecipesViewHolder(@NonNull View itemView) {

        super(itemView);

        savedRecipeName = itemView.findViewById(R.id.savedRecipeName);
        cookbook_list_container = itemView.findViewById(R.id.cookbook_list_container);
    }
}
