package com.dextra.rpaura.sandwichapp.view.impl;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.OrderTO;
import com.dextra.rpaura.sandwichapp.presenter.impl.CartPresenter;
import com.dextra.rpaura.sandwichapp.view.IDetailLunch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dextra.rpaura.sandwichapp.util.Generics.formatCurrency;
import static com.dextra.rpaura.sandwichapp.util.Generics.makeIngredients;

public class DetailActivity extends AppCompatActivity implements IDetailLunch {

    @BindView(R.id.ad_imv)
    AppCompatImageView imvRowDetail;
    @BindView(R.id.ad_txt_name)
    TextView tvRowDetailName;
    @BindView(R.id.ad_txt_price)
    TextView tvRowDetailPrice;
    @BindView(R.id.ad_txt_ingredients)
    TextView tvRowDetailIngredients;
    @BindView(R.id.fab_plus)
    FloatingActionButton fabPlus;
    @BindView(R.id.fab_cart)
    FloatingActionButton fabCart;
    @BindView(R.id.fab_custom)
    FloatingActionButton fabCustom;

    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen =false;

    private Sandwich sandwich;
    private CartPresenter cartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        cartPresenter = new CartPresenter(this);

        if (!getIntent().getExtras().isEmpty()) {
            sandwich = getIntent().getExtras().getParcelable("lunch");
        }

        showDetailLunch(sandwich);
    }

    private void animateFab(){
        if(isOpen){
            fabPlus.startAnimation(rotateForward);
            fabCart.startAnimation(fabClose);
            fabCart.setClickable(false);
            fabCart.setVisibility(View.INVISIBLE);
            fabCustom.startAnimation(fabClose);
            fabCustom.setClickable(false);
            fabCustom.setVisibility(View.INVISIBLE);

            isOpen = false;
        }else{
            fabPlus.startAnimation(rotateBackward);
            fabCart.startAnimation(fabOpen);
            fabCart.setClickable(true);
            fabCart.setVisibility(View.VISIBLE);
            fabCustom.startAnimation(fabOpen);
            fabCustom.setClickable(true);
            fabCustom.setVisibility(View.VISIBLE);
            isOpen = true;
        }
    }
    @Override
    public void showDetailLunch(Sandwich lunch) {
        Uri uri = Uri.parse(lunch.getImage());
        Picasso.get().load(uri).into(imvRowDetail);
        tvRowDetailName.setText(lunch.getName());
        tvRowDetailPrice.setText(formatCurrency(lunch.getPriceTotal()));
        tvRowDetailIngredients.setText(makeIngredients(lunch.getIngredients()));

    }

    @Override
    public void sendSucess(OrderTO orderTO) {
        Toast.makeText(this, "Sandwich adicionado com sucesso.", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void sendError() {
        Toast.makeText(this, "Erro ao adionar item !", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_cart)
    public void sendOrder(){
        if(!sandwich.getExtras().isEmpty()){
            cartPresenter.addItemCartWithExtras(sandwich.getId(),Stream.of(sandwich.getExtras())
                    .map(IngredientTO::getId)
                    .toList());
        }else{
            cartPresenter.addItemCart(sandwich.getId());
        }
    }

    @OnClick(R.id.fab_plus)
    public void clickFab(){
        animateFab();
    }

    @OnClick(R.id.fab_custom)
    public void customizeOrder(){
        showDialogCustomize();
    }

    public void showDialogCustomize() {
        ExtrasDialog dialog = new ExtrasDialog(this);
        dialog.setResultExtrasSelect(this::makeExtras);
        dialog.show();
    }

    private void makeExtras(Map<Long, Integer> extras) {
        sandwich.setName(sandwich.getName().concat(" - do seu jeito"));
        sandwich.setExtras(new ArrayList<>());

        Stream.of(extras).flatMap(this::makeExtrasEntry)
                .forEach(key -> sandwich.getExtras().add(recuperIngrediente(key)));

        showDetailLunch(sandwich);
    }

    private Stream<Long> makeExtrasEntry(Map.Entry<Long, Integer> extraEntry) {
        List<Long> extrasKey = new ArrayList<>();
        for(int x = 0; x < extraEntry.getValue(); x++){
            extrasKey.add(extraEntry.getKey());
        }
        return Stream.of(extrasKey);
    }

    private IngredientTO recuperIngrediente(Long key) {
        return cartPresenter.findIngredientByKey(key);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cartPresenter.onStop();
    }
}
