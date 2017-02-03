package com.twobonkers.hungry.presentation.details;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Ingredient;
import com.twobonkers.hungry.databinding.ItemIngredientBinding;

import java.util.List;

public class IngredientItem extends AbstractItem<IngredientItem, IngredientItem.ViewHolder> {

    private static final ViewHolderFactory<? extends IngredientItem.ViewHolder> FACTORY = new IngredientItem.ItemFactory();

    protected static class ItemFactory implements ViewHolderFactory<IngredientItem.ViewHolder> {
        public IngredientItem.ViewHolder create(View v) {
            return new IngredientItem.ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends IngredientItem.ViewHolder> getFactory() {
        return FACTORY;
    }

    private Ingredient ingredient;

    public IngredientItem(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public int getType() {
        return R.id.tv_ingredient;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_ingredient;
    }

    @Override
    public void bindView(IngredientItem.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.binding.setIngredient(ingredient);
    }

    @Override
    public void unbindView(IngredientItem.ViewHolder holder) {
        super.unbindView(holder);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemIngredientBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemIngredientBinding.bind(itemView);
        }
    }
}
