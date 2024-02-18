package com.example.cookingovereasy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the list that will appear when an ingredient is being searched for.
 */
public class SearchIngredientAdapter extends RecyclerView.Adapter
        <SearchIngredientAdapter.ViewHolder>{

    Context context;
    private List<SearchIngredient> searchIngredients;
    AddToGroceryList listener;

    /**
     * Constructor for the ingredient search recycler view.
     * @param context
     * @param ingredientsList List of ingredients to be searched through.
     * @param listener
     */
    public SearchIngredientAdapter(Context context, ArrayList<SearchIngredient> ingredientsList,
                                   AddToGroceryList listener) {

        this.context = context;
        this.searchIngredients = ingredientsList;
        this.listener = listener;
    }

    /**
     * Populates the recycler view with the filtered list of ingredients  after the user puts
     * something into the search bar.
     * @param filteredList a filtered list of ingredients.
     */
    public void setFilteredList(List<SearchIngredient> filteredList) {

        this.searchIngredients = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Created the view holder for the ingredient search.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return the view holder.
     */
    @NonNull
    @Override
    public SearchIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_ingredient_search,
                parent, false);

        return new ViewHolder(view, listener);
    }

    /**
     * Populated the view holder once created.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SearchIngredientAdapter.ViewHolder holder, int position) {

        SearchIngredient ingredient = searchIngredients.get(position);
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addIngredient(ingredient.getIngredientName());
            }
        });
    }

    /**
     * Returns the number of ingredients you can search through.
     * @return number of ingredients.
     */
    @Override
    public int getItemCount() {
        return searchIngredients.size();
    }

    /**
     * Creates a recycler view for the ingredient search.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredientName;
        private final ImageView addButton;
        AddToGroceryList listener;

        /**
         * Constructor for the recycler view holder for the ingredient search.
         * @param itemView
         * @param listener
         */
        public ViewHolder(@NonNull View itemView, AddToGroceryList listener) {
            super(itemView);
            this.listener = listener;
            ingredientName = itemView.findViewById(R.id.textView_title);
            addButton = itemView.findViewById(R.id.addToGroceryListIcon);
        }
    }

    /**
     * Interface that will allow you to add an ingredient from the search to the grocery list.
     */
    public static interface AddToGroceryList {

        void addIngredient(String newIngredient);
    }

}
