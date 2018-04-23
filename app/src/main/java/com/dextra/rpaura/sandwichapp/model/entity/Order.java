
package com.dextra.rpaura.sandwichapp.model.entity;

import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public class Order {

    private Long date;
    private List<IngredientTO> extras;
    private Long id;
    private Sandwich sandwich;

    public Order() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public List<IngredientTO> getExtras() {
        return extras;
    }

    public void setExtras(List<IngredientTO> extras) {
        this.extras = extras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", extras=" + extras +
                ", id=" + id +
                ", sandwich=" + sandwich +
                '}';
    }
}
