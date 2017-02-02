package com.twobonkers.hungry.presentation.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.twobonkers.hungry.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RetryItem extends AbstractItem<RetryItem, RetryItem.ViewHolder> {

    public static final int IDENTIFIER = 1338;
    private static final ViewHolderFactory<? extends RetryItem.ViewHolder> FACTORY = new RetryItem.ItemFactory();

    @Override
    public int getType() {
        return R.id.btn_retry;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_retry;
    }

    @Override
    public void bindView(RetryItem.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.btnRetry.setOnClickListener(v -> holder.itemView.callOnClick());
    }

    @Override
    public void unbindView(RetryItem.ViewHolder holder) {
        holder.btnRetry.setOnClickListener(null);
    }

    protected static class ItemFactory implements ViewHolderFactory<RetryItem.ViewHolder> {
        public RetryItem.ViewHolder create(View v) {
            return new RetryItem.ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_retry) Button btnRetry;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}