package com.dextra.rpaura.sandwichapp.model.entity.promotions;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public class MuitoQueijo implements Promotion {
    @Override
    public Double calcTotal(List<IngredientTO> ingredientTOS) {
        //A cada 3 porções de queijo o cliente só paga 2. Se o lanche tiver 6 porções, o cliente pagará 4. Assim por diante
        long countQueijoDesconto = Stream.of(ingredientTOS).filter(ingrediente -> ingrediente.getName().contains("Queijo")).count() / 3;

        double valorQueijo = Stream.of(ingredientTOS)
                .filter(ingrediente -> ingrediente.getName().contains("Queijo"))
                .mapToDouble(IngredientTO::getPrice)
                .max()
                .orElse(0.00);
        return valorQueijo * countQueijoDesconto;
    }
}
