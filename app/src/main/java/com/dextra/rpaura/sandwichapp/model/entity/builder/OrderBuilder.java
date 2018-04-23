package com.dextra.rpaura.sandwichapp.model.entity.builder;



import com.dextra.rpaura.sandwichapp.model.entity.Order;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public class OrderBuilder {
    private Order instance;

    public OrderBuilder() {
        this.instance = new Order();
    }

    public OrderBuilder withDate(Long date){
        this.instance.setDate(date);
        return this;
    }
    public OrderBuilder withId(Long id){
        this.instance.setId(id);
        return this;
    }
    public OrderBuilder withSandwich(Sandwich sandwich){
        this.instance.setSandwich(sandwich);
        return this;
    }

    public OrderBuilder withExtras(List<IngredientTO> extras){
        this.instance.setExtras(extras);
        return this;
    }

    public Order builder(){
        return this.instance;
    }
}
