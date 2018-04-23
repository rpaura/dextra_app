package com.dextra.rpaura.sandwichapp.view;


import com.dextra.rpaura.sandwichapp.model.entity.Order;

import java.util.List;


public interface ICartFragment {
    void showOrderOnIU(List<Order> orders);
    void showProgress();
    void hideProgress();
}
