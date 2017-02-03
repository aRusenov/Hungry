package com.twobonkers.hungry.presentation.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.twobonkers.hungry.R;
import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.databinding.ItemFeedBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedItem extends AbstractItem<FeedItem, FeedItem.ViewHolder> {

    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();

    protected static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }

    private Recipe recipe;

    public FeedItem(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public long getIdentifier() {
        return recipe.id();
    }

    @Override
    public int getType() {
        return R.id.recipe_feed;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_feed;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.binding.setRecipe(recipe);

        Glide.with(holder.itemView.getContext())
                .load(recipe.previewImageUrl())
                .into(holder.ivPreview);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        Glide.clear(holder.ivPreview);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFeedBinding binding;

        @BindView(R.id.iv_preview) ImageView ivPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            binding = ItemFeedBinding.bind(itemView);
        }
    }
}
