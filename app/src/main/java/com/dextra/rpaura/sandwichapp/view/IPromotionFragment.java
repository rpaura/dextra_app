package com.dextra.rpaura.sandwichapp.view;

import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.PromotionTO;

import java.util.List;

public interface IPromotionFragment {
    void showPromotionOnIU(List<PromotionTO> promotions);
    void showProgress();
    void hideProgress();
}
