package com.dextra.rpaura.sandwichapp.presenter.impl;


import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;
import com.dextra.rpaura.sandwichapp.model.remote.impl.RestClientAPI;
import com.dextra.rpaura.sandwichapp.presenter.ICartPresenter;
import com.dextra.rpaura.sandwichapp.view.IDetailLunch;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CartPresenter implements ICartPresenter {
    private IDetailLunch mIDetailLunch;
    private RestClientAPI mRestClientAPI;
    private CompositeSubscription mCompositeSubscription;

    public CartPresenter(IDetailLunch mIDetailLunch) {
        this.mIDetailLunch = mIDetailLunch;
        this.mCompositeSubscription = new CompositeSubscription();
        mRestClientAPI = RestClientAPI.getInstance();
    }

    @Override
    public void addItemCart(Long id) {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .sendOrder(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mIDetailLunch::sendSucess, Throwable::printStackTrace)
        );
    }

    @Override
    public void addItemCartWithExtras(Long id, List<Long> extras) {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .sendOrderWithExtras(id,extras.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mIDetailLunch::sendSucess, Throwable::printStackTrace)
        );
    }

    @Override
    public IngredientTO findIngredientByKey(Long key) {
        return mRestClientAPI
                .getAPI()
                .ingredientFindAll()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(ingredienteAPIs -> ingredienteAPIs)
                .filter(ingredient -> ingredient.getId().equals(key))
                .toBlocking()
                .single();
    }

    @Override
    public void onStop() {
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }
}
