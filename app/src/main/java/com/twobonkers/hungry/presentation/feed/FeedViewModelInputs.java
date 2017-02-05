package com.twobonkers.hungry.presentation.feed;

public interface FeedViewModelInputs {

    /**
     * Invoke when next page should be fetched.
     */
    void nextPage();

    /**
     * Invoke when recipe feed should be refreshed.
     */
    void refresh();

    /**
     * Invoke when the previous failed request should be retried.
     */
    void clickRetry();
}
