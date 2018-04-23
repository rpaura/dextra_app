package com.dextra.rpaura.sandwichapp.presenter.impl;


import com.dextra.rpaura.sandwichapp.model.entity.builder.SandwichBuilder;
import com.dextra.rpaura.sandwichapp.model.remote.impl.RestClientAPI;
import com.dextra.rpaura.sandwichapp.presenter.ILunchPresenter;
import com.dextra.rpaura.sandwichapp.view.ISandwichFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class LunchPresenter  implements ILunchPresenter {
    private ISandwichFragment mISandwichFragment;
    private RestClientAPI mRestClientAPI;
    private CompositeSubscription mCompositeSubscription;

    public LunchPresenter(ISandwichFragment mISandwichFragment) {
        this.mISandwichFragment = mISandwichFragment;
        this.mCompositeSubscription = new CompositeSubscription();
        mRestClientAPI = RestClientAPI.getInstance();
    }

    @Override
    public void loadLunchs() {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .lunchFindAll()
                        .flatMapIterable(lanches -> lanches)
                        .flatMap(lanche -> mRestClientAPI
                                .getAPI()
                                .ingredientFindById(lanche.getId())
                                .subscribeOn(Schedulers.io())
                                .map(ingredientes -> new SandwichBuilder()
                                        .withId(lanche.getId())
                                        .withName(lanche.getName())
                                        .withImage(lanche.getImage())
                                        .withIngredients(ingredientes)
                                        .builder()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toSortedList((l1, l2) -> l1.getName().compareTo(l2.getName()))
                        .subscribe(mISandwichFragment::showLunchOnIU, Throwable::printStackTrace)
        );
    }

    @Override
    public void onStop() {
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }
}
