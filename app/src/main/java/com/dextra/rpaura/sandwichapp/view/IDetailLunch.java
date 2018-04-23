package com.dextra.rpaura.sandwichapp.view;


import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.OrderTO;

public interface IDetailLunch {
    void showDetailLunch(Sandwich lunch);
    void sendSucess(OrderTO orderTO);
    void sendError();
}
