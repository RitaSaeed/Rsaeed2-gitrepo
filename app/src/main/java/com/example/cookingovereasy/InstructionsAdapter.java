package com.example.cookingovereasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingovereasy.Models.InstructionsResponse;

import java.util.List;

/**
 * The adapter for the recycler view that holds the instructions on the recipe details page.
 */
public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder>{

    Context context;
    List<InstructionsResponse> list;

    /**
     * Creates an instance of the instructions adapter.
     * @param context
     * @param list
     */
    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {

        this.context = context;
        this.list = list;
    }

    /**
     * Creates the view holder of the items in the recycler view.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return InstructionsViewHolder
     */
    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new InstructionsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_instructions, parent, false));
    }

    /**
     * Functionality for when the view holder has been created and bound.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {

        holder.recycler_instruction_steps.setHasFixedSize(true);
        holder.recycler_instruction_steps.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        InstructionStepAdapter stepAdapter = new InstructionStepAdapter(context,
                list.get(position).steps);
        holder.recycler_instruction_steps.setAdapter(stepAdapter);
    }

    /**
     * Returns the number of recycler view objects.
     * @return integer representing size
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}

/**
 * Inner class that represents the view holder, provides access to attributes of object in
 * the recycler view.
 */
class InstructionsViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recycler_instruction_steps;

    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        recycler_instruction_steps = itemView.findViewById(R.id.recycler_instruction_steps);
    }
}
