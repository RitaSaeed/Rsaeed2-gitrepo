package com.example.cookingovereasy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.metrics.Event;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the ingredients and categories in the grocery list.
 */
public class IngredientAdapter extends RecyclerView.Adapter implements
        ItemMoveCallback.ItemTouchHelperContract{

    Context context;
    ArrayList<Ingredient> ingredientArrayList;
    EventListener listener;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    /**
     * Constructor for the ingredient adapter.
     * @param ingredientArrayList an Array List of ingredients that will be displayed.
     */
    public IngredientAdapter(ArrayList<Ingredient> ingredientArrayList, Context context,
                             EventListener listener) {

        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
        this.listener = listener;
    }

    /**
     * Returns if the item in the grocery list is a category or a grocery item.
     * @param position position to query
     * @return
     */
    public int getItemViewType(int position){

        if(ingredientArrayList.get(position).name.toLowerCase().contains("protein") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("bread and grains") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("dairy") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("vegetables") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("fruit") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("misc")){
            return 2;
        }else{
            return 1;
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v;

        if(viewType == 2){
            v = layoutInflater.inflate(R.layout.grocery_categories, parent, false);
            return new MyViewHolder2(v);
        }else{
            v = layoutInflater.inflate(R.layout.grocery_item, parent, false);
            return new ViewHolderOne(v);
        }
    }



    /**
     * Manages the checkboxes so they can be references as checked or unchecked.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(ingredientArrayList.get(position).name.toLowerCase().contains("protein")  ||
                ingredientArrayList.get(position).name.toLowerCase().contains("bread and grains") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("dairy") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("vegetables") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("fruit") ||
                ingredientArrayList.get(position).name.toLowerCase().contains("misc")){
            MyViewHolder2 holder2 = (MyViewHolder2) holder;
            holder2.categoryName.setText(ingredientArrayList.get(position).name);
        }else {
            ViewHolderOne holder1 = (ViewHolderOne) holder;
            pref =  context.getSharedPreferences("shared preferences", MODE_PRIVATE);
            Ingredient ingredient = ingredientArrayList.get(position);
            holder1.checkBox.setChecked(ingredientArrayList.get(position).getSelected());
            holder1.ingredientName.setText(ingredient.name);

            holder1.checkBox.setTag(position);
            holder1.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor = pref.edit();
                    Integer pos = (Integer) holder1.checkBox.getTag();

                    if (ingredientArrayList.get(pos).getSelected()) {
                        ingredientArrayList.get(pos).setSelected(false);
                        editor.putBoolean(String.valueOf(holder1.checkBox.getId()),false);
                    } else {
                        ingredientArrayList.get(pos).setSelected(true);
                        editor.putBoolean(String.valueOf(holder1.checkBox.getId()), true);
                    }
                    editor.commit();
                }
            });
        }
    }

    /**
     * Returns number of ingredients.
     * @return
     */
    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    /**
     * Gives the ingredient list drag and drop functionality to organize the list.
     * @param fromPosition an int being the starting position of the card.
     * @param toPosition an int being the ending position of the card.
     */
    @Override
    public void onRowMoved(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(ingredientArrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(ingredientArrayList, i, i - 1); // causing error
                // index out of bounds index 3 size 0 when dragging third added item on new list
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        listener.onEvent();
    }

    /**
     * Will change the color of a checkbox once selected.
     * @param viewHolder the checkbox.
     */
    @Override
    public void onRowSelected(RecyclerView.ViewHolder viewHolder) {

        viewHolder.itemView.setBackgroundColor(Color.parseColor("#d9dedb"));
    }

    /**
     * Allows the checkbox to remain white until selected.
     * @param viewHolder the checkbox.
     */
    @Override
    public void onRowClear(RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof IngredientAdapter.ViewHolderOne) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffcc"));
        }
        else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#fee569"));
        }

    }

    /**
     * Creates a card that will contain ingredient names and the checkboxes to
     * be added to a recycler view that will appear as a list.
     */
    public static class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView ingredientName;
        CheckBox checkBox;
        View rowView;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            rowView = itemView;
            checkBox = itemView.findViewById(R.id.checkBoxIngredient);
            ingredientName = itemView.findViewById(R.id.ingredientName);
        }
    }

    /**
     * Creates a card that will contain the category name for a category view in the groceyr list.
     */
    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView categoryName;
        View rowView;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            rowView = itemView;
            categoryName = itemView.findViewById(R.id.groceryCategoryName);
        }
    }

    /**
     *EventListener for grocery list.
     */
    public interface EventListener {
        void onEvent();
    }

}
