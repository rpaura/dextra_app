package com.dextra.rpaura.sandwichapp.model.entity.builder;


import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;


public class IngredientBuilder {
    private IngredientTO instance;

    public IngredientBuilder() {
        this.instance = new IngredientTO();
    }

    public IngredientBuilder withId(Long id){
        this.instance.setId(id);
        return this;
    }

    public IngredientBuilder withImage(String image){
        this.instance.setImage(image);
        return this;
    }
    public IngredientBuilder withName(String name){
        this.instance.setName(name);
        return this;
    }
    public IngredientBuilder withPrice(Double price){
        this.instance.setPrice(price);
        return this;
    }
    public IngredientTO builder(){
        return this.instance;
    }
}
