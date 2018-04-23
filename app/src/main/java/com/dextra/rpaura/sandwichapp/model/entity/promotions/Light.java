package com.dextra.rpaura.sandwichapp.model.entity.promotions;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public class Light implements Promotion {
    @Override
    public Double calcTotal(List<IngredientTO> ingredientTOS) {
        //Se o lanche tem alface e não tem bacon, ganha 10% de desconto.
        boolean isAlface = Stream.of(ingredientTOS).anyMatch(ingrediente -> ingrediente.getName().contains("Alface"));
        boolean isNotBacon =  Stream.of(ingredientTOS).noneMatch(ingrediente -> ingrediente.getName().contains("Bacon"));

        if(isAlface && isNotBacon){
            return Stream.of(ingredientTOS).mapToDouble(IngredientTO::getPrice).sum() * 0.10;
        }else{
            return 0.0;
        }
    }
}
