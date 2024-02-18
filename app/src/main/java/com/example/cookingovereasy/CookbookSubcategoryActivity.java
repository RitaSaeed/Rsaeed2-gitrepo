package com.example.cookingovereasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;

import java.util.ArrayList;

public class CookbookSubcategoryActivity extends AppCompatActivity implements MyRecipeAdapter.MyRecipeListener {

    TextView cookbookNameHeader;
    ImageView backToCookbook;
    RecyclerView cookbookItems;
    ArrayList<MyRecipe> personalRecipes;
    MyRecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_subcategory);
        getViews();
        personalRecipes = (ArrayList<MyRecipe>) getIntent().getSerializableExtra("myRecipes");

        backToCookbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cookbookItems.setLayoutManager(new LinearLayoutManager(CookbookSubcategoryActivity.this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new MyRecipeAdapter(CookbookSubcategoryActivity.this,
                personalRecipes, this);
        cookbookItems.setAdapter(adapter);
        cookbookItems.setHasFixedSize(true);
    }

    private void getViews() {
        cookbookNameHeader = findViewById(R.id.cookbookNameHeader);
        backToCookbook = findViewById(R.id.backToCookbook);
        cookbookItems = findViewById(R.id.cookbookItems);
    }

    @Override
    public void onMyRecipeClick(int position) {
        startActivity(new Intent(CookbookSubcategoryActivity.this, MyRecipeDetails.class)
                .putExtra("recipe", personalRecipes.get(position)));
    }
}