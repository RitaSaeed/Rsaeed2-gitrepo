package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.Result;

import java.util.List;

/**
 * Currently deprecated, kept for potential functionality. **
 *
 * Used to search through an arraylist of result objects.
 */
public class IngredientSearchAdapter extends RecyclerView.Adapter<IngredientSearchViewHolder> {

    Context context;
    List<Result> list;
    String currentItem;

    public IngredientSearchAdapter(Context context, List<Result> list){

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new IngredientSearchViewHolder(LayoutInflater.from(context).inflate
                (R.layout.list_ingredient_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientSearchViewHolder holder, int position) {

        holder.textView_title.setText(list.get(position).name);
        holder.textView_title.setSelected(true);

        holder.addToGroceryListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentItem = list.get(holder.getAdapterPosition()).name;
                //Toast.makeText(context, "Added " + currentItem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setFilteredList(List<Result> filteredList){

        this.list = filteredList;
        notifyDataSetChanged();
    }

    public String getCurrentItem() {
        return currentItem;
    }

}

class IngredientSearchViewHolder extends RecyclerView.ViewHolder{

    CardView ingredient_list_container;
    TextView textView_title;
    ImageView addToGroceryListIcon;

    public IngredientSearchViewHolder(@NonNull View itemView){

        super(itemView);

        ingredient_list_container = itemView.findViewById(R.id.ingredient_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        addToGroceryListIcon = itemView.findViewById(R.id.addToGroceryListIcon);
    }
}
