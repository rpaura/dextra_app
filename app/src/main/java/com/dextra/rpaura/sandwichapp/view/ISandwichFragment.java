package com.dextra.rpaura.sandwichapp.view;



import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;

import java.util.List;

public interface ISandwichFragment {
    void showLunchOnIU(List<Sandwich> lunchs);
    void showProgress();
    void hideProgress();
}
