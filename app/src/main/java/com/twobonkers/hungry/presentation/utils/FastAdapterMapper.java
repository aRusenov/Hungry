package com.twobonkers.hungry.presentation.utils;

import com.twobonkers.hungry.presentation.feed.FeedItem;
import com.twobonkers.hungry.data.models.RecipeFeedModel;

import java.util.ArrayList;
import java.util.List;

public class FastAdapterMapper {

    public static List<FeedItem> toFeedItems(List<RecipeFeedModel> recipes) {
        List<FeedItem> feedItems = new ArrayList<>(recipes.size());
        for (int i = 0; i < recipes.size(); i++) {
            feedItems.add(new FeedItem(recipes.get(i)));
        }

        return feedItems;
    }
}
