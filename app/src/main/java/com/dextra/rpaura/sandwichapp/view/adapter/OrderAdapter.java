package com.dextra.rpaura.sandwichapp.view.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.entity.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dextra.rpaura.sandwichapp.util.Generics.formatCurrency;
import static com.dextra.rpaura.sandwichapp.util.Generics.makeIngredients;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Order order = this.orders.get(position);

        if(order.getSandwich().getImage() != null) {
            Uri uri = Uri.parse(order.getSandwich().getImage());
            Picasso.get().load(uri).into(holder.imv_order_sandwich);
        }

        holder.tv_order_sandwich_name.setText(order.getSandwich().getName());
        holder.tv_order_sandwich_price.setText(formatCurrency(order.getSandwich().getPriceTotal()));
        holder.tv_order_sandwich_ingredients.setText(makeIngredients(order.getSandwich().getIngredients()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_order_sandwich)ImageView imv_order_sandwich;
        @BindView(R.id.tv_row_order_sandwich_name)TextView tv_order_sandwich_name;
        @BindView(R.id.tv_row_order_sandwich_price)TextView tv_order_sandwich_price;
        @BindView(R.id.tv_row_order_sandwich_ingredients)TextView tv_order_sandwich_ingredients;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


