package com.dextra.rpaura.sandwichapp.presenter.impl;


import com.dextra.rpaura.sandwichapp.model.remote.impl.RestClientAPI;
import com.dextra.rpaura.sandwichapp.presenter.IPromotionPresenter;
import com.dextra.rpaura.sandwichapp.view.IPromotionFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PromotionPresenter implements IPromotionPresenter {
    private IPromotionFragment mIPromotionFragment;
    private RestClientAPI mRestClientAPI;
    private CompositeSubscription mCompositeSubscription;

    public PromotionPresenter(IPromotionFragment mIPromotionFragment) {
        this.mIPromotionFragment = mIPromotionFragment;
        this.mCompositeSubscription = new CompositeSubscription();
        mRestClientAPI = RestClientAPI.getInstance();
    }

    @Override
    public void loadPromotions() {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .promotionFindAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mIPromotionFragment::showPromotionOnIU, Throwable::printStackTrace)
        );
    }

    @Override
    public void onStop() {
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }
}
