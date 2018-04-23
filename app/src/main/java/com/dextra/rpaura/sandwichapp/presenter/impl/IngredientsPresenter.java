package com.dextra.rpaura.sandwichapp.presenter.impl;

import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.remote.impl.RestClientAPI;
import com.dextra.rpaura.sandwichapp.presenter.IIngredientPresenter;
import com.dextra.rpaura.sandwichapp.view.IExtrasDialog;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class IngredientsPresenter implements IIngredientPresenter {
    private IExtrasDialog mIExtrasDialog;
    private RestClientAPI mRestClientAPI;
    private CompositeSubscription mCompositeSubscription;

    public IngredientsPresenter(IExtrasDialog mIExtrasDialog) {
        this.mIExtrasDialog = mIExtrasDialog;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mRestClientAPI = RestClientAPI.getInstance();
    }

    @Override
    public void loadIngredients() {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .ingredientFindAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mIExtrasDialog::showIngredientsOnIU, Throwable::printStackTrace)
        );
    }

    @Override
    public void onStop() {
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }
}
