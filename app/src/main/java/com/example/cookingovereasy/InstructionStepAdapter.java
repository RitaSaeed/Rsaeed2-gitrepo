package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.Step;

import java.util.List;

/**
 * A class used by the InstructionsAdapter, necessary for the way that Spoonacular sends out
 * the analyzed instructions for recipes.
 */
public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{

    Context context;
    List<Step> list;

    /**
     * Creates an instance of the Instruction Step Adapater
     * @param context current context
     * @param list list of steps
     */
    public InstructionStepAdapter(Context context, List<Step> list) {

        this.context = context;
        this.list = list;
    }

    /**
     * Creates a view holder.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return InstructionStepViewHolder
     */
    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new InstructionStepViewHolder(LayoutInflater.from(context).
                inflate(R.layout.list_instructions_steps, parent, false));
    }

    /**
     * Adds functionality for when the view holder is bound. Establishes the recipe steps in order.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {

        holder.textView_instructions_step_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instructions_step_title.setText(list.get(position).step);
    }

    /**
     * Returns the number of steps that the recipe has.
     * @return integer representing num of steps
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}

/**
 * View Holder that gives access to the various attributes of the object within recycler view.
 */
class InstructionStepViewHolder extends RecyclerView.ViewHolder {

    TextView textView_instructions_step_number, textView_instructions_step_title;

    public InstructionStepViewHolder(@NonNull View itemView) {

        super(itemView);

        textView_instructions_step_number =
                itemView.findViewById(R.id.textView_instructions_step_number);
        textView_instructions_step_title =
                itemView.findViewById(R.id.textView_instructions_step_title);
    }
}