package com.example.cookingovereasy;

import android.content.Context;

import com.example.cookingovereasy.Models.IngredientResponse;
import com.example.cookingovereasy.Models.InstructionsResponse;
import com.example.cookingovereasy.Models.RandomRecipeApiResponse;
import com.example.cookingovereasy.Models.RecipeDetailsResponse;
import com.example.cookingovereasy.listeners.IngredientResponseListener;
import com.example.cookingovereasy.listeners.InstructionsListener;
import com.example.cookingovereasy.listeners.RandomRecipeResponseListener;
import com.example.cookingovereasy.listeners.RecipeDetailsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Manages the requests made to the Spoonacular API.
 */
public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * Constructor for the request manager.
     * @param context
     */
    public RequestManager(Context context) {
        this.context = context;
    }

    /**
     * Call this from the main activity to get the recipes
     * @param listener
     */
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe
                (context.getString(R.string.spoonKey), "20", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call,
                                   Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    /**
     * Retreived the ingredients for a recipe from the API.
     * @param listener
     * @param includeChildren
     * @param query
     */
    public void getIngredients(IngredientResponseListener listener, boolean includeChildren,
                               String query) {
        CallIngredients callIngredients = retrofit.create(CallIngredients.class);
        Call<IngredientResponse> call = callIngredients.callIngredient
                (context.getString(R.string.spoonKey), "20", query, includeChildren);
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call,
                                   Response<IngredientResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    /**
     * Retrieves details pertaining to a recipe.
     * @param listener waits for an instruction.
     * @param id id of the recipe.
     */
    public void getRecipeDetails(RecipeDetailsListener listener, int id) {
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails
                (id, context.getString(R.string.spoonKey));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call,
                                   Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }
            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    /**
     * Retrieves the instructors for a recipe.
     * @param listener waits for a request.
     * @param id of the the recipe being requested.
     */
    public void getInstructions(InstructionsListener listener, int id) {
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions
                (id, context.getString(R.string.spoonKey));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call,
                                   Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    /**
     * Interface that provides the call method for calling random recipes
     */
    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    /**
     * Interface that calls the ingredients for a recipe from the API.
     */
    private interface CallIngredients {
        @GET("food/ingredients/search")
        Call<IngredientResponse> callIngredient(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("query") String query,
                @Query("addChildren") boolean includeChildren
        );
    }

    /**
     * Interface that calls the details for a recipe from the API.
     */
    private interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    /**
     * Interface that calls the instructions to complete a recipe from the API.
     */
    private interface CallInstructions {
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
