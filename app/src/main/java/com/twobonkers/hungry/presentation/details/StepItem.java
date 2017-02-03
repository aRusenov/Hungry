package com.twobonkers.hungry.presentation.details;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Step;
import com.twobonkers.hungry.databinding.ItemStepBinding;

import java.util.List;

public class StepItem extends AbstractItem<StepItem, StepItem.ViewHolder> {

    private static final ViewHolderFactory<? extends StepItem.ViewHolder> FACTORY = new StepItem.ItemFactory();

    protected static class ItemFactory implements ViewHolderFactory<StepItem.ViewHolder> {
        public StepItem.ViewHolder create(View v) {
            return new StepItem.ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends StepItem.ViewHolder> getFactory() {
        return FACTORY;
    }

    private Step Step;

    public StepItem(Step Step) {
        this.Step = Step;
    }

    public Step getStep() {
        return Step;
    }

    @Override
    public int getType() {
        return R.id.tv_description;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_step;
    }

    @Override
    public void bindView(StepItem.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.binding.setStep(Step);
    }

    @Override
    public void unbindView(StepItem.ViewHolder holder) {
        super.unbindView(holder);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemStepBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemStepBinding.bind(itemView);
        }
    }
}