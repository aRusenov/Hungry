package com.twobonkers.hungry.presentation.feed;

import com.twobonkers.hungry.data.models.Recipe;
import com.twobonkers.hungry.data.remote.GetRecipesResponse;
import com.twobonkers.hungry.data.remote.RecipesService;
import com.twobonkers.hungry.domain.ApiPager;
import com.twobonkers.hungry.domain.lib.rx.Transformers;
import com.twobonkers.hungry.presentation.details.RecipeChangeBus;
import com.twobonkers.hungry.presentation.views.FragmentViewModel;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class FeedViewModel extends FragmentViewModel<FeedFragment> implements FeedViewModelInputs, FeedViewModelOutputs {

    public final FeedViewModelInputs inputs = this;
    public final FeedViewModelOutputs outputs = this;

    private ConcurrentArrayList<Recipe> allRecipes = new ConcurrentArrayList<>();

    private PublishSubject<Void> loadMore = PublishSubject.create();
    private PublishSubject<Integer> startOver = PublishSubject.create();
    private PublishSubject<Void> retryClicked = PublishSubject.create();
    private PublishSubject<Void> refresh = PublishSubject.create();
    private BehaviorSubject<Integer> nextPage = BehaviorSubject.create(0);

    private BehaviorSubject<List<Recipe>> recipes = BehaviorSubject.create(Collections.emptyList());
    private BehaviorSubject<Boolean> loading = BehaviorSubject.create();
    private BehaviorSubject<Boolean> showRetry = BehaviorSubject.create();
    private BehaviorSubject<Void> lastPage = BehaviorSubject.create();
    private Observable<Throwable> error;

    public FeedViewModel(RecipesService service, RecipeChangeBus recipeChangeBus) {
        ApiPager<GetRecipesResponse, GetRecipesResponse, Integer> apiPager = ApiPager.<GetRecipesResponse, GetRecipesResponse, Integer>builder()
                .loadMore(loadMore)
                .startOver(startOver)
                .makeRequest(page -> service.getRecipes(page, "Bearer HI8TWT963CD1l6gdWfx0CbzPh-5hHKeoU8yuvq3KGEgpJp2TGbL0hy1FowH_IRg25duoKQh5iNvXgc7gD3e3mokc4LkMCAqNywTtoc7x6LRUWVelmo38VFAGk4N9fOJicrZsPfkFnjbiJjr64BTIw7lZUm_HXvrXvdWiCaXaLrH_VpLpalDitavgv2v3uCv26qNh2R25EToYuldxOo4LJVwLAWyk3NN1cGTuFC-olkb98G5x6ubkc0ff4piZnr53CzYEZtI6hbLuU3M28QeAz-BfG2TOlVyTy9juUwnxD5xUMv_ZQD85CfStgZK_bV2JBunfa1Ui9Sl1Sud1_TG2Hy1qQ9BbjNCrebwkIbXrxWLVKDO03UOw_KqAwINjO78cPjjjJYNaFS6LYtvBQvv_jaj9A3UthBtvhAHNoyu9Y4EwbByMyGmw9Rq7H6pLYEdGQzIB3PsD3ybnjhnd2SoE53gduHopC9UubyO3umDouZ2S44Y-ws6QL3fk9bWRphdV"))
                .mapResponse(response -> response)
                .stopWhen(response -> response.page() >= response.totalPages())
                .nextPageParams(page -> page + 1)
                .build();

        recipeChangeBus.recipeChanged()
                .compose(bindToLifecycle())
                .subscribe(this::updateRecipe);

        apiPager.pages()
                .compose(bindToLifecycle())
                .doOnNext(response -> nextPage.onNext(response.page()))
                .map(GetRecipesResponse::recipes)
                .subscribe(recipes -> {
                    allRecipes.addAll(recipes);
                    this.recipes.onNext(allRecipes.toList());
                });

        apiPager.loading()
                .compose(bindToLifecycle())
                .subscribe(loading);

        error = apiPager.error()
                .compose(bindToLifecycle());

        apiPager.lastPage()
                .compose(bindToLifecycle())
                .subscribe(lastPage);

        nextPage.compose(bindToLifecycle())
                .compose(Transformers.takeWhen(retryClicked))
                .subscribe(page -> {
                    showRetry.onNext(false);
                    startOver.onNext(page + 1);
                });

        refresh.subscribe(__ -> {
            allRecipes.clear();
            recipes.onNext(Collections.emptyList());
            startOver.onNext(1);
        });

        error.subscribe(__ -> showRetry.onNext(true));

        startOver.onNext(nextPage.getValue() + 1);
    }

    private void updateRecipe(Recipe newRecipe) {
        allRecipes.replace(newRecipe);
        recipes.onNext(allRecipes.toList());
    }

    @Override
    public void nextPage() {
        loadMore.onNext(null);
    }

    @Override
    public void refresh() {
        refresh.onNext(null);
    }

    @Override
    public void clickRetry() {
        retryClicked.onNext(null);
    }

    @Override
    public Observable<Void> onLastPage() {
        return lastPage;
    }

    @Override
    public Observable<Boolean> showRetry() {
        return showRetry;
    }

    @Override
    public Observable<Boolean> showLoading() {
        return loading;
    }

    @Override
    public Observable<List<Recipe>> recipes() {
        return recipes;
    }

    @Override
    public Observable<Throwable> error() {
        return error;
    }
}
