package com.dextra.rpaura.sandwichapp.presenter.impl;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.entity.builder.SandwichBuilder;
import com.dextra.rpaura.sandwichapp.model.entity.builder.OrderBuilder;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;
import com.dextra.rpaura.sandwichapp.model.remote.impl.RestClientAPI;
import com.dextra.rpaura.sandwichapp.presenter.IOrderPresenter;
import com.dextra.rpaura.sandwichapp.view.ICartFragment;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class OrderPresenter implements IOrderPresenter {
    private ICartFragment mICartFragment;
    private RestClientAPI mRestClientAPI;
    private CompositeSubscription mCompositeSubscription;

    public OrderPresenter(ICartFragment mICartFragment) {
        this.mICartFragment = mICartFragment;
        this.mCompositeSubscription = new CompositeSubscription();
        mRestClientAPI = RestClientAPI.getInstance();
    }

    @Override
    public void loadOrders() {
        mCompositeSubscription.add(
                mRestClientAPI
                        .getAPI()
                        .orderFindAll()
                        .flatMapIterable(pedidos -> pedidos)
                        .map(pedido -> new OrderBuilder()
                                .withId(pedido.getId())
                                .withDate(pedido.getDate())
                                .withSandwich(findLunch(pedido.getId_sandwich(),pedido.getExtras()))
                                .builder()
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toList()
                        .subscribe(mICartFragment::showOrderOnIU, Throwable::printStackTrace));
    }

    private List<IngredientTO> findIngredientsByIds(List<Long> extras) {
        return Stream.of(extras).map(this::findIngreByKey).toList();
    }

    private IngredientTO findIngreByKey(Long id) {
        return mRestClientAPI
                .getAPI()
                .ingredientFindAll()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(ingres -> ingres)
                .filter(ingre -> ingre.getId().equals(id))
                .toBlocking()
                .single();
    }

    private Sandwich findLunch(String id, List<Long> extras) {
        return mRestClientAPI
                .getAPI()
                .lunchFindById(id)
                .subscribeOn(Schedulers.io())
                .map(lancheAPI -> new SandwichBuilder()
                        .withId(lancheAPI.getId())
                        .withImage(lancheAPI.getImage())
                        .withName(lancheAPI.getName())
                        .withIngredients(findIngredients(lancheAPI.getId()))
                        .withExtras(findIngredientsByIds(extras))
                        .builder())
                .toBlocking()
                .single();
    }

    private List<IngredientTO> findIngredients(Long id) {
        return mRestClientAPI.getAPI()
                .ingredientFindById(id)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(ingredienteAPIs -> ingredienteAPIs)
                .toList()
                .toBlocking()
                .single();
    }

    @Override
    public void onStop() {
        if (!this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }
}
