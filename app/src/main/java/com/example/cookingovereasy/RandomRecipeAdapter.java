package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.Recipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recycler view in the search fragment.
 */
public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder> {

    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    /**
     * Creates an instance of the Random Recipe Adapter.
     * @param context current context
     * @param list list of random recipes (length of 10 currently)
     * @param listener listener used for the on click attribute of each recipe
     */
    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {

        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    /**
     * Creates the view holder for the objects in recycler view.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return RandomRecipeViewHolder
     */
    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate
                (R.layout.list_random_recipe, parent, false));
    }

    /**
     * Gives functionality to view holder once it is bound.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {

        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.imageView_food);

        holder.search_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    /**
     * Returns the number of random recipes that are in the recycler view. (currently always 10)
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Used in search function, updates list to include filtered data only.
     * @param filteredList
     */
    public void setFilteredList(List<Recipe> filteredList) {

        this.list = filteredList;
        notifyDataSetChanged();
    }
}

/**
 * Creates a way to access the attributes of an object in the recycler view.
 */
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView search_list_container;
    TextView textView_title;
    ImageView imageView_food;

    public RandomRecipeViewHolder(@NonNull View itemView) {

        super(itemView);

        search_list_container = itemView.findViewById(R.id.search_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}
