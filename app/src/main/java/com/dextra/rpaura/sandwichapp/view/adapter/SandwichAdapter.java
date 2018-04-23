package com.dextra.rpaura.sandwichapp.view.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dextra.rpaura.sandwichapp.util.Generics.formatCurrency;
import static com.dextra.rpaura.sandwichapp.util.Generics.makeIngredients;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.MyViewHolder> {
    private static ClickList clickList;
    private List<Sandwich> sandwiches;

    public SandwichAdapter(List<Sandwich> sandwiches, ClickList clickList) {
        this.sandwiches = sandwiches;
        this.clickList = clickList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sandwich, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Sandwich sandwich = this.sandwiches.get(position);

        if(sandwich.getImage() != null) {
            Uri uri = Uri.parse(sandwich.getImage());
            Picasso.get().load(uri).into(holder.rs_imv);
        }

        holder.rs_txt_name.setText(sandwich.getName());
        holder.rs_txt_price.setText(formatCurrency(sandwich.getPriceTotal()));
        holder.rs_txt_ingredients.setText(makeIngredients(sandwich.getIngredients()));
    }

    @Override
    public int getItemCount() {
        return sandwiches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rs_imv)ImageView rs_imv;
        @BindView(R.id.rs_txt_name)TextView rs_txt_name;
        @BindView(R.id.rs_txt_price)TextView rs_txt_price;
        @BindView(R.id.rs_txt_ingredients)TextView rs_txt_ingredients;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> clickList.onClickItem(sandwiches.get(getLayoutPosition())));
        }
    }
}


