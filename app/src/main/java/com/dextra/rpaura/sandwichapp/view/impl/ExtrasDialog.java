package com.dextra.rpaura.sandwichapp.view.impl;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;
import com.dextra.rpaura.sandwichapp.presenter.impl.IngredientsPresenter;
import com.dextra.rpaura.sandwichapp.view.ExtrasSelectListener;
import com.dextra.rpaura.sandwichapp.view.IExtrasDialog;
import com.dextra.rpaura.sandwichapp.view.adapter.IngredientsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExtrasDialog extends Dialog implements IExtrasDialog {

    @BindView(R.id.ai_rv_add)
    RecyclerView ai_rv_add;
    @BindView(R.id.ai_bt_save)
    Button ai_bt_save;
    @BindView(R.id.ai_bt_cancel)
    Button ai_bt_cancel;

    private IngredientsPresenter presenter;
    private IngredientsAdapter adapter;
    private ExtrasSelectListener extrasSelectListener;

    public ExtrasDialog(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ButterKnife.bind(this);
        ai_rv_add.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        presenter = new IngredientsPresenter(this);
        presenter.loadIngredients();
    }

    @Override
    public void showIngredientsOnIU(List<IngredientTO> ingredientTOS) {
        updateIngredients(ingredientTOS);
    }

    private void updateIngredients(List<IngredientTO> ingredients) {
        adapter = new IngredientsAdapter(ingredients);
        ai_rv_add.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ai_bt_cancel)
    public void cancel(){
        dismiss();
    }

    @OnClick(R.id.ai_bt_save)
    public void clickSave(){
        if (extrasSelectListener != null) {
            extrasSelectListener.finish(adapter.getExtras());
        }
        ExtrasDialog.this.dismiss();
    }

    public void setResultExtrasSelect(ExtrasSelectListener extrasSelectListener) {
        this.extrasSelectListener = extrasSelectListener;
    }
}