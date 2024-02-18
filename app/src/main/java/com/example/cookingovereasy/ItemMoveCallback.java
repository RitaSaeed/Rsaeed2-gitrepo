package com.example.cookingovereasy;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Class that handles the movement in the grocery list, specifically what happens when
 * an item is dragged.
 */
public class ItemMoveCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperContract myAdapter;
    public ItemMoveCallback(ItemTouchHelperContract adapter) {
        myAdapter = adapter;
    }

    /**
     * Check for the long press enabled. (default true)
     * @return true
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * Check for the itemViewSwipe enabled. This would be used if you wanted to have a
     * specific point for users to tap before dragging a component.
     * @return false by default
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * Gets the movement flags for the draggable item, i.e. the change in array values.
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached.
     * @param viewHolder   The ViewHolder for which the movement information is necessary.
     * @return integer representing the movement changes.
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * Moves the rows when dragged from starting position to target position.
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
     * @param viewHolder   The ViewHolder which is being dragged by the user.
     * @param target       The ViewHolder over which the currently active item is being
     *                     dragged.
     * @return true for checks
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {

        myAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        // causing error
        // index out of bounds index 3 size 0 when dragging third added item on new list
        return true;
    }

    /**
     * This is the method that is used when itemSwipe is enabled.
     * @param viewHolder The ViewHolder which has been swiped by the user.
     * @param direction  The direction to which the ViewHolder is swiped. It is one of
     *                   {@link #UP}, {@link #DOWN},
     *                   {@link #LEFT} or {@link #RIGHT}. If your
     *                   {@link #getMovementFlags(RecyclerView, ViewHolder)}
     *                   method
     *                   returned relative flags instead of {@link #LEFT} / {@link #RIGHT};
     *                   `direction` will be relative as well. ({@link #START} or {@link
     *                   #END}).
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // currently not implemented
    }

    /**
     * Updates the view to reflect changes made
     * @param viewHolder  The new ViewHolder that is being swiped or dragged. Might be null if
     *                    it is cleared.
     * @param actionState One of {@link ItemTouchHelper#ACTION_STATE_IDLE},
     *                    {@link ItemTouchHelper#ACTION_STATE_SWIPE} or
     *                    {@link ItemTouchHelper#ACTION_STATE_DRAG}.
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof RecyclerView.ViewHolder) {
                RecyclerView.ViewHolder myViewHolder =
                        (RecyclerView.ViewHolder) viewHolder;
                myAdapter.onRowSelected(myViewHolder);
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * Clears the current row after something is dragged from it
     * @param recyclerView The RecyclerView which is controlled by the ItemTouchHelper.
     * @param viewHolder   The View that was interacted by the user.
     */
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {

        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof RecyclerView.ViewHolder) {
            RecyclerView.ViewHolder myViewHolder =
                    (RecyclerView.ViewHolder) viewHolder;
            myAdapter.onRowClear(myViewHolder);
        }
    }

    /**
     * Interface for the grocery list item updates.
     */
    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(RecyclerView.ViewHolder myViewHolder);
        void onRowClear(RecyclerView.ViewHolder myViewHolder);
    }
}
