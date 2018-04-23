package com.dextra.rpaura.sandwichapp.presenter;


import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public interface ICartPresenter {
    void addItemCart(Long id);
    void addItemCartWithExtras(Long id, List<Long> extras);
    IngredientTO findIngredientByKey(Long key);
    void onStop();
}
