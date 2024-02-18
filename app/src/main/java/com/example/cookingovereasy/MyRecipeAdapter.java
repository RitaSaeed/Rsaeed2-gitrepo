package com.example.cookingovereasy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyRecipe> myCreatedRecipes;
    MyRecipeListener listener;


    public MyRecipeAdapter(Context context, ArrayList<MyRecipe> myCreatedRecipes, MyRecipeListener listener){
        this.context = context;
        this.myCreatedRecipes = myCreatedRecipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recipe_user_input_item, parent, false);

        return new MyRecipeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeAdapter.MyViewHolder holder, int position) {
        MyRecipe recipe = myCreatedRecipes.get(position);
        holder.recipeName.setText(recipe.title);
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMyRecipeClick(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return myCreatedRecipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        View rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rowView = itemView;
            recipeName = itemView.findViewById(R.id.myRecipeName);//**********

        }
    }
    public interface MyRecipeListener {
        void onMyRecipeClick(int position);
    }
}


