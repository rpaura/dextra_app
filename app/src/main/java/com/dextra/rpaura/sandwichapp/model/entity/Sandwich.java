package com.dextra.rpaura.sandwichapp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.model.entity.promotions.PromotionEnum;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.ArrayList;
import java.util.List;

public class Sandwich implements Parcelable {
    private Long id;
    private String image;
    private List<IngredientTO> ingredients;
    private String name;
    private List<IngredientTO> extras;

    public Sandwich() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<IngredientTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientTO> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientTO> getExtras() {
        return extras;
    }

    public void setExtras(List<IngredientTO> extras) {
        this.extras = extras;
    }

    public Double getPriceTotal() {
        double extras = 0.0;
        double ingredients = Stream.of(this.ingredients).mapToDouble(IngredientTO::getPrice).sum();

        if(this.extras != null && !this.extras.isEmpty()) {
            this.ingredients.addAll(this.extras);
            extras = Stream.of(this.extras).mapToDouble(IngredientTO::getPrice).sum();
        }
        return  ingredients + extras - checkPromotion();
    }

    private Double checkPromotion() {
        return  PromotionEnum.LIGHT.getPromocao().calcTotal(ingredients)
                + PromotionEnum.MUITA_CARNE.getPromocao().calcTotal(ingredients)
                + PromotionEnum.MUITO_QUEIJO.getPromocao().calcTotal(ingredients);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.image);
        dest.writeTypedList(this.ingredients);
        dest.writeString(this.name);
        dest.writeList(this.extras);
    }

    protected Sandwich(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.image = in.readString();
        this.ingredients = in.createTypedArrayList(IngredientTO.CREATOR);
        this.name = in.readString();
        this.extras = new ArrayList<>();
        in.readList(this.extras, Long.class.getClassLoader());
    }

    public static final Creator<Sandwich> CREATOR = new Creator<Sandwich>() {
        @Override
        public Sandwich createFromParcel(Parcel source) {
            return new Sandwich(source);
        }

        @Override
        public Sandwich[] newArray(int size) {
            return new Sandwich[size];
        }
    };

    @Override
    public String toString() {
        return "Sandwich{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", name='" + name + '\'' +
                ", extras=" + extras +
                '}';
    }
}
