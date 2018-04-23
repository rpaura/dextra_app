package com.dextra.rpaura.sandwichapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.PromotionTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> {
    private static ClickList clickList;
    private List<PromotionTO> promotions;

    public PromotionAdapter(List<PromotionTO> promotions, ClickList clickList) {
        this.promotions = promotions;
        this.clickList = clickList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_promotion, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        PromotionTO promotion = this.promotions.get(position);

        holder.tv_name_promotion.setText(promotion.getName());
        holder.tv_desc_promotion.setText(promotion.getDescription());
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_row_promotion_name)TextView tv_name_promotion;
        @BindView(R.id.tv_row_promotion_desc)TextView tv_desc_promotion;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> clickList.onClickItem(promotions.get(getLayoutPosition())));
        }
    }
}


