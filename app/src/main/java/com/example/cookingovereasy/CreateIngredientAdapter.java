package com.example.cookingovereasy;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateIngredientAdapter extends RecyclerView.Adapter<CreateIngredientAdapter.MyViewHolder> {
    ArrayList<Ingredient> ingredientArrayList;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /**
     * Constructor for the ingredient adapter.
     *
     * @param ingredientArrayList an Array List of ingredients that will be displayed.
     */
    public CreateIngredientAdapter(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    /**
     * Returns what each ingredient card is defined as.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public CreateIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_my_recipe_user_input_item, parent, false);

        return new CreateIngredientAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.ingredientName.setText(ingredient.name);
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        View rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rowView = itemView;
            ingredientName = itemView.findViewById(R.id.myRecipeName); // might have to rename stepText

        }
    }
}
