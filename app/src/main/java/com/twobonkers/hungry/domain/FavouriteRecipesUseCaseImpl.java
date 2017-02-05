package com.twobonkers.hungry.domain;

import com.twobonkers.hungry.data.remote.PostFavouriteResponse;
import com.twobonkers.hungry.data.remote.RecipesService;

import rx.Observable;

public class FavouriteRecipesUseCaseImpl implements FavouriteRecipeUseCase {

    private RecipesService recipesService;

    public FavouriteRecipesUseCaseImpl(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @Override
    public Observable<PostFavouriteResponse> favourite(long recipeId) {
        return recipesService.favouriteRecipe(recipeId, "Bearer HI8TWT963CD1l6gdWfx0CbzPh-5hHKeoU8yuvq3KGEgpJp2TGbL0hy1FowH_IRg25duoKQh5iNvXgc7gD3e3mokc4LkMCAqNywTtoc7x6LRUWVelmo38VFAGk4N9fOJicrZsPfkFnjbiJjr64BTIw7lZUm_HXvrXvdWiCaXaLrH_VpLpalDitavgv2v3uCv26qNh2R25EToYuldxOo4LJVwLAWyk3NN1cGTuFC-olkb98G5x6ubkc0ff4piZnr53CzYEZtI6hbLuU3M28QeAz-BfG2TOlVyTy9juUwnxD5xUMv_ZQD85CfStgZK_bV2JBunfa1Ui9Sl1Sud1_TG2Hy1qQ9BbjNCrebwkIbXrxWLVKDO03UOw_KqAwINjO78cPjjjJYNaFS6LYtvBQvv_jaj9A3UthBtvhAHNoyu9Y4EwbByMyGmw9Rq7H6pLYEdGQzIB3PsD3ybnjhnd2SoE53gduHopC9UubyO3umDouZ2S44Y-ws6QL3fk9bWRphdV");
    }
}
