package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.Recipe;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Search adapter to be used by recycler view in Search fragment
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    List<Food> foodArrayList;

    /**
     * Constructor, establishes context and the array list holding the data.
     * @param context
     * @param foodArrayList
     */
    public SearchAdapter(Context context, ArrayList<Food> foodArrayList) {

        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    /**
     * Used in search function, updates list to include filtered data only.
     * @param filteredList
     */
    public void setFilteredList(List<Food> filteredList) {

        this.foodArrayList = filteredList;
        notifyDataSetChanged();
    }

    /**
     * Creates the view holder and inflates it to include the search item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return viewHolder with search item added
     */
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.search_item, parent,
                false);

        return new MyViewHolder(v);
    }

    /**
     * Updates the heading and image in the recycler view to hold data from the food object.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {

        Food food = foodArrayList.get(position);
        holder.tvHeading.setText(food.heading);
        holder.titleImage.setImageResource(food.titleImage);
    }

    /**
     * Gets the size of the food array as an integer.
     * @return size of foodArrayList
     */
    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    /**
     * Nested class that creates the ViewHolder. This establishes what elements go into the
     * ViewHolder, including the food name and image, as well as a separator bar.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView titleImage;
        TextView tvHeading;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            cardView = itemView.findViewById(R.id.recycler_view_search);
            titleImage = itemView.findViewById(R.id.title_image);
            tvHeading = itemView.findViewById(R.id.tvHeading);
        }
    }
}
