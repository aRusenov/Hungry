package com.twobonkers.hungry.presentation.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.twobonkers.hungry.R;

import java.util.List;

public class LoadingItem extends AbstractItem<LoadingItem, LoadingItem.ViewHolder> {

    public static final int IDENTIFIER = 1;
    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new LoadingItem.ItemFactory();

    @Override
    public int getType() {
        return R.id.pb_loading;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_loading;
    }

    @Override
    public void bindView(LoadingItem.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
    }

    @Override
    public void unbindView(LoadingItem.ViewHolder holder) {

    }

    protected static class ItemFactory implements ViewHolderFactory<LoadingItem.ViewHolder> {
        public LoadingItem.ViewHolder create(View v) {
            return new LoadingItem.ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends LoadingItem.ViewHolder> getFactory() {
        return FACTORY;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }
}
