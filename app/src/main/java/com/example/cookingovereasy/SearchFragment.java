package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookingovereasy.Models.RandomRecipeApiResponse;
import com.example.cookingovereasy.Models.Recipe;
import com.example.cookingovereasy.Models.SavedRecipe;
import com.example.cookingovereasy.listeners.RandomRecipeResponseListener;
import com.example.cookingovereasy.listeners.RecipeClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality for the search fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Category> cookbookCategories;
    private List<Recipe> recipeList;
    private SearchView searchView;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    ProgressDialog dialog;
    View listenerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();

    /**
     * Creates an inflated layout of the search fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return inflatedLayout
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         return inflater.inflate(R.layout.fragment_search, container, false);
    }

    /**
     * Pulls the view from onCreateView and does the bulk of the logic with it.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        loadData();
        manager = new RequestManager(getActivity());
        listenerView = view;
        spinner = view.findViewById(R.id.spinnerSearch);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.tags,
                R.layout.search_spinner_text
        );

        arrayAdapter.setDropDownViewResource(R.layout.search_spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);
        searchView = view.findViewById(R.id.searchViewSearch);
        searchView.clearFocus();

        /**
         * QueryTextListener for the search bar.
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Response lister for the random recipes that will populate the recipe search recycler view
     * before an item is searched for.
     */
    private final RandomRecipeResponseListener randomRecipeResponseListener =
            new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {

            dialog.dismiss();
            recyclerView = listenerView.findViewById(R.id.recycler_view_search);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            randomRecipeAdapter = new RandomRecipeAdapter(getActivity(), response.recipes,
                    recipeClickListener);
            recipeList = response.recipes;
            recyclerView.setAdapter(randomRecipeAdapter);
            randomRecipeAdapter.notifyDataSetChanged();
            // following 2 lines just close the keyboard after hitting search
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }

        /**
         * Message displayed upon an error.
         * @param message string that is displayed when there is an error.
         */
        @Override
        public void didError(String message) {

            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Filters the food arrayList based on the current text in the search field.
     * @param text
     */
    private void filterList(String text) {

        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(recipe);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "Recipe not found", Toast.LENGTH_SHORT).show();
        } else {
            randomRecipeAdapter.setFilteredList(filteredList);
        }
    }

    /**
     *
     */
    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new
            AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            manager.getRandomRecipes(randomRecipeResponseListener, tags);
            dialog.show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {

        @Override
        public void onRecipeClicked(String id) {

            Intent intent = new Intent(getContext(), RecipeDetails.class);
            intent.putExtra("id", id);
            intent.putExtra("categories", cookbookCategories);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            String category = data.getStringExtra("category");
            String name = data.getStringExtra("name");
            int id = data.getIntExtra("id", 0);
            ((HomePage)getActivity()).addSavedRecipe(new SavedRecipe(category, name, id));
        }
    }

    private void loadData(){
        cookbookCategories = ((HomePage)getActivity()).retrieveCategories();
    }

    public void passFavorite(String recipeID, String recipeName, String category) {}
}