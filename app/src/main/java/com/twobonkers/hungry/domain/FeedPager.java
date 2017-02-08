package com.twobonkers.hungry.domain;

import android.support.annotation.Nullable;

import com.twobonkers.hungry.data.local.preferences.LocalUserRepository;
import com.twobonkers.hungry.data.remote.GetRecipesResponse;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.util.UserUtils;

import rx.Observable;

public class FeedPager {

    private RecipesService service;
    private @Nullable String authToken;

    public FeedPager(RecipesService service, LocalUserRepository localUserRepository) {
        this.service = service;
        authToken = UserUtils.getBearerToken(localUserRepository.currentUser());
    }

    public FeedPager.Builder builder() {
        ApiPager.Builder<GetRecipesResponse, GetRecipesResponse, Integer> builderEnvelope = ApiPager.<GetRecipesResponse, GetRecipesResponse, Integer>builder()
                .makeRequest(page -> service.getRecipes(page, authToken))
                .mapResponse(response -> response)
                .stopWhen(response -> response.page() >= response.totalPages())
                .nextPageParams(page -> page + 1);

        return new Builder(builderEnvelope);
    }

    public static class Builder {

        private ApiPager.Builder<GetRecipesResponse, GetRecipesResponse, Integer> wrappedBuilder;

        public Builder(ApiPager.Builder<GetRecipesResponse, GetRecipesResponse, Integer> wrappedBuilder) {
            this.wrappedBuilder = wrappedBuilder;
        }

        public Builder loadMore(Observable<Void> loadMore) {
            wrappedBuilder.loadMore(loadMore);
            return this;
        }

        public Builder startOver(Observable<Integer> startOver) {
            wrappedBuilder.startOver(startOver);
            return this;
        }

        public ApiPager<GetRecipesResponse, GetRecipesResponse, Integer> build() {
            return wrappedBuilder.build();
        }
    }
}
