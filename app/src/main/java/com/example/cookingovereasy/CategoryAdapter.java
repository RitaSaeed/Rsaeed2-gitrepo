package com.example.cookingovereasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.SavedRecipe;

import java.util.ArrayList;

/**
 * Adapter for the recycler view in the category activity.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Category> createdCategories;
    EventListener listener;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /**
     * Constructor for the category adapter.
     * @param createdCategories arraylist of categories
     * @param context current context
     */
    public CategoryAdapter(ArrayList<Category> createdCategories, Context context, EventListener
            listener) {

        this.context = context;
        this.createdCategories = createdCategories;
        this.listener = listener;
    }

    /**
     * Sets up view holder.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cookbook_category,
                parent, false);

        return new CategoryAdapter.MyViewHolder(v);
    }

    /**
     * On bind adds functionality of items.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {

        Category currentItem = createdCategories.get(position);
        holder.categoryName.setText(currentItem.getName());

        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryClicked(currentItem.getName());
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(currentItem);
            }
        });
    }

    /**
     * Returns number of items in this recyclerview.
     * @return
     */
    @Override
    public int getItemCount() {
        return createdCategories.size();
    }

    /**
     * Creates the view holder of the components of item in recycler view.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        View rowView;
        ImageView remove;

        /**
         * ViewHolder for the category view.
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            rowView = itemView;
            categoryName = itemView.findViewById(R.id.categoryName);
            remove = itemView.findViewById(R.id.removecategory);

        }
    }

    /**
     * Interface to interact with the category class.
     */
    public interface EventListener {

        void onRemove(Category item);
        void onCategoryClicked(String categoryName);
    }
}
