package com.dextra.rpaura.sandwichapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.IngredientTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    private List<IngredientTO> ingredients;
    private Map<Long,Integer> extras = new HashMap<>();

    public IngredientsAdapter(List<IngredientTO> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<Long, Integer> getExtras() {
        return extras;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_add_ingre, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        IngredientTO ingredient = this.ingredients.get(position);

        holder.tv_row_add_name.setText(ingredient.getName());

        holder.np_row_add_qtd.setMaxValue(50);
        holder.np_row_add_qtd.setMinValue(0);
        holder.np_row_add_qtd.setOnValueChangedListener((numberPicker, oldVl, newVl) -> extras.put(ingredient.getId(),newVl));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rai_tv_name)TextView tv_row_add_name;
        @BindView(R.id.np_row_add_qtd)
        NumberPicker np_row_add_qtd;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


