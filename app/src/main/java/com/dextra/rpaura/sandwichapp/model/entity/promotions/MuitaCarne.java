package com.dextra.rpaura.sandwichapp.model.entity.promotions;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;


public class MuitaCarne implements Promotion {
    @Override
    public Double calcTotal(List<IngredientTO> ingredientTOS) {
        //A cada 3 porções de carne o cliente só paga 2. Se o lanche tiver 6 porções, o cliente pagará 4. Assim por diante
        long countCarneDesconto = Stream.of(ingredientTOS).filter(ingrediente -> ingrediente.getName().contains("Hamburguer de Carne")).count() / 3;

        double valorCarne = Stream.of(ingredientTOS)
                .filter(ingrediente -> ingrediente.getName().contains("Hamburguer de Carne"))
                .mapToDouble(IngredientTO::getPrice)
                .max()
                .orElse(0.00);
        return valorCarne * countCarneDesconto;
    }
}