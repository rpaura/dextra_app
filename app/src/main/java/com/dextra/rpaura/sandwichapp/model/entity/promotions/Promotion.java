package com.dextra.rpaura.sandwichapp.model.entity.promotions;


import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public interface Promotion {
    Double calcTotal(List<IngredientTO> ingredientTOS);
}
