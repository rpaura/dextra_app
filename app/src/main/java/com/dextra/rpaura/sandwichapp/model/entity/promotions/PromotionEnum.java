package com.dextra.rpaura.sandwichapp.model.entity.promotions;


public enum PromotionEnum {
    LIGHT {
        @Override
        public Promotion getPromocao() {
            return new Light();
        }
    },
    MUITA_CARNE {
        @Override
        public Promotion getPromocao() {
            return new MuitaCarne();
        }
    },
    MUITO_QUEIJO {
        @Override
        public Promotion getPromocao() {
            return new MuitoQueijo();
        }
    };

    public abstract Promotion getPromocao();
}
