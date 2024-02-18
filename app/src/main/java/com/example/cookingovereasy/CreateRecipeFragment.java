package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Provides functionality for the create recipe fragment, which will allow users to input a number
 * of attributes that represents recipe, which will then be stored inside of their CookBook. This
 * will be implemented after the API is connected to further determine what values are necessary
 */
public class CreateRecipeFragment extends Fragment {

    Button addIngredient;
    Button addStep;

    Button saveRecipe;
    EditText dialogEditTextIngredient;
    EditText dialogEditTextInstruction;
    EditText titleText;
    AlertDialog addIngredientDialog;
    AlertDialog AddInstructionDialog;


    ArrayList<Ingredient> myIngredients = new ArrayList<>();
    ArrayList<RecipeStep> mySteps = new ArrayList<>();

    private CreateRecipeStepAdapter recipeStepAdapter;
    private CreateIngredientAdapter myIngredientAdapter;

    private RecyclerView ingredientRecyclerView;
    private RecyclerView instructionRecyclerView;


    /**
     * Initial onCreateView method that is called when the fragment view is created.
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set up recycler view for ingredients:
        ingredientRecyclerView = view.findViewById(R.id.ingredient_recycler);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myIngredientAdapter = new CreateIngredientAdapter(myIngredients);
        ingredientRecyclerView.setAdapter(myIngredientAdapter);
        ingredientRecyclerView.setHasFixedSize(false);

        //set up recycler view for recipe steps:
        instructionRecyclerView = view.findViewById(R.id.instruction_recycler);
        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeStepAdapter = new CreateRecipeStepAdapter(mySteps);
        instructionRecyclerView.setAdapter(recipeStepAdapter);
        instructionRecyclerView.setHasFixedSize(false);

        //initialize buttons
        addIngredient = view.findViewById(R.id.addIngredient);
        addStep = view.findViewById(R.id.addInstruction);
        saveRecipe = view.findViewById(R.id.saveRecipe);
        titleText = view.findViewById(R.id.editRecipeTitle);

        //loadData

        createIngredientDialog();
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredientDialog.show();
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "Added ingredient!", Toast.LENGTH_SHORT).show();
            }
        });

        createInstructionDialog();
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddInstructionDialog.show();
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "Added instruction!", Toast.LENGTH_SHORT).show();
            }
        });

        saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeTitle = titleText.getText().toString();
                ArrayList<Ingredient> tempIngredients = myIngredientAdapter.ingredientArrayList;
                ArrayList<RecipeStep> tempRecipeSteps = recipeStepAdapter.stepArrayList;
                MyRecipe recipe = new MyRecipe(recipeTitle,
                        tempIngredients, tempRecipeSteps);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Recipe Saved!", Toast.LENGTH_SHORT).show();
                ((HomePage)getActivity()).addCustomRecipe(recipe);
                clearFields();

            }
        });

    }

    private void clearFields() {
        titleText.setText("");
//        for (int i = 0; i < myIngredientAdapter.ingredientArrayList.size(); i++) {
//            myIngredientAdapter.ingredientArrayList.remove(i);
//            myIngredientAdapter.notifyItemRemoved(i);
//        }
        myIngredientAdapter.ingredientArrayList.removeAll(myIngredientAdapter.ingredientArrayList);
        myIngredientAdapter.notifyDataSetChanged();
        recipeStepAdapter.stepArrayList.removeAll(recipeStepAdapter.stepArrayList);
        recipeStepAdapter.notifyDataSetChanged();


//        for (int i = 0; i < recipeStepAdapter.stepArrayList.size(); i++) {
//            recipeStepAdapter.stepArrayList.remove(i);
//            recipeStepAdapter.notifyItemRemoved(i);
//        }
    }

    private void createIngredientDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        dialogEditTextIngredient = view.findViewById(R.id.editName);
        builder.setView(view);
        builder.setTitle("Add Ingredient")
                .setPositiveButton("Add ingredient", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addIngredient(dialogEditTextIngredient.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        addIngredientDialog = builder.create();
    }

    private void addIngredient(String dialogEditText) {
        View view = getLayoutInflater().inflate(R.layout.my_recipe_user_input_item, null);
        TextView name = view.findViewById(R.id.myRecipeName);
        name.setText(dialogEditText);
        myIngredients.add(new Ingredient(name.getText().toString()));
        myIngredientAdapter.notifyItemInserted(myIngredients.size());
        myIngredientAdapter.notifyDataSetChanged();
        //saveData();
    }

    private void createInstructionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        dialogEditTextInstruction = view.findViewById(R.id.editName);
        builder.setView(view);
        builder.setTitle("Add Instruction")
                .setPositiveButton("Add instruction", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addInstruction(dialogEditTextInstruction.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AddInstructionDialog = builder.create();
    }

    private void addInstruction(String dialogEditText) {
        View view = getLayoutInflater().inflate(R.layout.my_recipe_user_input_item, null);
        TextView name = view.findViewById(R.id.myRecipeName);
        name.setText(dialogEditText);
        recipeStepAdapter.stepArrayList.add(new RecipeStep(name.getText().toString()));
        recipeStepAdapter.notifyItemInserted(recipeStepAdapter.stepArrayList.size());
        recipeStepAdapter.notifyDataSetChanged();
        //saveData();
    }
}