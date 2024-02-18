package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RecipeClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds the views/functionality for the cookbook fragment.
 */
public class CookBookFragment extends Fragment implements CategoryAdapter.EventListener{

    CardView myCookBook; //declaring buttons
    Button myCategories;
    AlertDialog dialog;
    ArrayList<Category> createdCategories;
    EditText nameCategory;
    Map<String, ArrayList<SavedRecipe>> categoryMap;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private GridLayoutManager glm;

    /**
     * Creates the view object to be referenced.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cook_book, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Functionality for when the view is created.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        categoryMap = new HashMap<>();
        myCookBook = view.findViewById(R.id.imageButton);  //referencing cookbook icon button
        myCategories = view.findViewById(R.id.addCategory); //referencing 'new categories' button
        // builds the recycler view
        recyclerView = view.findViewById(R.id.recycler_cookbook_view);
        glm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(glm);
        adapter = new CategoryAdapter(createdCategories, getActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        loadData();

        myCookBook.setOnClickListener(new View.OnClickListener() {  //adding a response when
                                                                    // cookbook button is clicked
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CookbookSubcategoryActivity.class)
                        .putExtra("myRecipes", ((HomePage)getActivity()).getCustomRecipes()));
            }
        });

        createDialog();
        myCategories.setOnClickListener(new View.OnClickListener() {  //adding a response when
                                                            // the new category button is clicked
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    /**
     * When you selected to create a new category, this method will bring up the popup to name
     * the category.
     */
    private void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        nameCategory = view.findViewById(R.id.editName);
        builder.setView(view);

        builder.setTitle("Create New Category")
                .setPositiveButton("Create Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCategory(nameCategory.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog = builder.create();
    }

    /**
     * Adds a new category to the recycler view.
     * @param categoryName new name of the category to be added
     */
    private void addCategory(String categoryName) {

        View view = getLayoutInflater().inflate(R.layout.cookbook_category, null);
        TextView name = view.findViewById(R.id.categoryName);
        name.setText(categoryName);
        adapter.createdCategories.add(new Category(name.getText().toString()));
        adapter.notifyItemInserted(adapter.createdCategories.size());
        adapter.notifyDataSetChanged();
        ((HomePage)getActivity()).addMapCategory(categoryName);
        saveData();
    }



    /**
     * Saves the categories to a share preference.
     */
    private void saveData(){

        SharedPreferences sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adapter.createdCategories);
        editor.putString("Created Categories", json);
        json = gson.toJson(categoryMap);
        editor.putString("ListCategories", json);
        editor.apply();
        ((HomePage)getActivity()).setCategories(adapter.createdCategories);
        ((HomePage)getActivity()).saveData();
    }

    /**
     * Loads the user-created categories from a shared preference.
     */
    public void loadData(){

        SharedPreferences sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("Created Categories", null);
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        adapter.createdCategories = gson.fromJson(json, type);

        if(adapter.createdCategories == null) {
            adapter.createdCategories = new ArrayList<>();
        }

        ((HomePage)getActivity()).setCategories(adapter.createdCategories);
    }

    /**
     * Method to remove a category and its data from the cookbook.
     * @param item
     */
    @Override
    public void onRemove(Category item) {

        adapter.createdCategories.remove(item);
        adapter.notifyDataSetChanged();
        saveData();
    }

    /**
     * When a category is clicked, this method will start the next activity that holds the
     * information of saved recipes in the category.
     * @param categoryName the name of the category clicked.
     */
    @Override
    public void onCategoryClicked(String categoryName) {

        ArrayList<SavedRecipe> categoryItems =
                ((HomePage)getActivity()).retrieveCategoryItems(categoryName);
        startActivity(new Intent(getContext(), Subcategory.class)
                    .putExtra("category", categoryName)
                    .putExtra("categoryItems", categoryItems));
    }
}



