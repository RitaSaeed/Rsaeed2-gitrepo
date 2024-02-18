package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.MyViewHolder>{
    ArrayList<RecipeStep> stepArrayList;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /**
     * Constructor for the com.example.cookingovereasy.RecipeStep adapter.
     * @param stepArrayList an Array List of ingredients that will be displayed.
     */
    public RecipeStepAdapter(ArrayList<RecipeStep> stepArrayList) {
        this.stepArrayList = stepArrayList;
    }

    @NonNull
    @Override
    public RecipeStepAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recipe_user_input_item,
                parent, false);

        return new RecipeStepAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapter.MyViewHolder holder, int position) {
        RecipeStep currentItem = stepArrayList.get(position);
        holder.stepText.setText(currentItem.getName());

    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stepText;
        View rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rowView = itemView;
            stepText = itemView.findViewById(R.id.myRecipeName);

        }
    }
}
