package com.dextra.rpaura.sandwichapp.view;



import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.List;

public interface IExtrasDialog {
    void showIngredientsOnIU(List<IngredientTO> ingredientTOS);
}
