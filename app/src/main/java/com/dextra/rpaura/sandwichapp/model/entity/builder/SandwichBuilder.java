package com.dextra.rpaura.sandwichapp.model.entity.builder;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 24/07/17.
 */

public class SandwichBuilder {
    private Sandwich instance;

    public SandwichBuilder() {
        this.instance = new Sandwich();
    }

    public SandwichBuilder withId(Long id){
        this.instance.setId(id);
        return this;
    }
    public SandwichBuilder withName(String name){
        this.instance.setName(name);
        return this;
    }
    public SandwichBuilder withImage(String image){
        this.instance.setImage(image);
        return this;
    }

    public SandwichBuilder withExtras(List<IngredientTO> extras){
        this.instance.setExtras(extras);
        return this;
    }
    public SandwichBuilder withIngredients(List<IngredientTO> ingredients){
        if(this.instance.getIngredients() == null){
            this.instance.setIngredients(new ArrayList<>());
        }

        Stream.of(ingredients).forEach(ingredienteAPI ->
                this.instance.getIngredients()
                        .add(new IngredientBuilder()
                                .withId(ingredienteAPI.getId())
                                .withImage(ingredienteAPI.getImage())
                                .withName(ingredienteAPI.getName())
                                .withPrice(ingredienteAPI.getPrice())
                                .builder()));

        return this;
    }

    public Sandwich builder(){
        return this.instance;
    }
}
