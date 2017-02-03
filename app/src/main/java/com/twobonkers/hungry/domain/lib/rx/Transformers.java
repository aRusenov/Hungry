package com.twobonkers.hungry.domain.lib.rx;

import android.support.annotation.NonNull;

import rx.Observable;

public class Transformers {

    /**
     * Emits the latest value of the source observable whenever the `when`
     * observable emits.
     */
    public static <S, T> TakeWhenTransformer<S, T> takeWhen(final @NonNull Observable<T> when) {
        return new TakeWhenTransformer<>(when);
    }

    /**
     * If called on the main thread, schedule the work immediately. Otherwise delay execution of the work by adding it
     * to a message queue, where it will be executed on the main thread.
     */
    public static @NonNull <T> ObserveForUITransformer<T> observeForUI() {
        return new ObserveForUITransformer<>();
    }
}