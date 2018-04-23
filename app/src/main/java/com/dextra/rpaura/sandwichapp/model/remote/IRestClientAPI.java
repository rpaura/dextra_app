package com.dextra.rpaura.sandwichapp.model.remote;

import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.OrderTO;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.SandwichTO;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.PromotionTO;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface IRestClientAPI {
    @GET("lanche")
    Observable<List<SandwichTO>> lunchFindAll();

    @GET("lanche/{id}")
    Observable<SandwichTO> lunchFindById(@Path("id") String id);

    @GET("ingrediente")
    Observable<List<IngredientTO>> ingredientFindAll();

    @GET("ingrediente/de/{id}")
    Observable<List<IngredientTO>> ingredientFindById(@Path("id") Long id);

    @GET("promocao")
    Observable<List<PromotionTO>> promotionFindAll();

    @GET("pedido")
    Observable<List<OrderTO>> orderFindAll();

    @PUT("pedido/{id_lanche}")
    Observable<OrderTO> sendOrder(@Path("id_lanche") Long id);

    @FormUrlEncoded
    @PUT("pedido/{id_lanche}")
    Observable<OrderTO> sendOrderWithExtras(@Path("id_lanche") Long id, @Field("extras") String extras);
}
