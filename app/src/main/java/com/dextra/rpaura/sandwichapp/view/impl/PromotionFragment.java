package com.dextra.rpaura.sandwichapp.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.dextra.rpaura.sandwichapp.R;
import com.dextra.rpaura.sandwichapp.model.entity.Sandwich;
import com.dextra.rpaura.sandwichapp.model.remote.entityAPI.PromotionTO;
import com.dextra.rpaura.sandwichapp.presenter.impl.PromotionPresenter;
import com.dextra.rpaura.sandwichapp.view.IPromotionFragment;
import com.dextra.rpaura.sandwichapp.view.adapter.ClickList;
import com.dextra.rpaura.sandwichapp.view.adapter.PromotionAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PromotionFragment extends Fragment implements IPromotionFragment, ClickList {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.rvPromotion)
    RecyclerView rvPromotion;

    Unbinder unbinder;
    private PromotionPresenter presenter;

    public static PromotionFragment newInstance() {
        return new PromotionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);

        unbinder = ButterKnife.bind(this, view);

        rvPromotion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvPromotion.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        presenter = new PromotionPresenter(this);
        presenter.loadPromotions();
        return view;
    }

    @Override
    public void showPromotionOnIU(List<PromotionTO> promotions) {
        updateTablePromotion(promotions);
        if (progress.isShown()) {
            hideProgress();
        }
    }

    @Override
    public void onClickItem(Object object) {
        Sandwich lunch = (Sandwich) object;
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("lunch", lunch);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onStop();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    private void updateTablePromotion(List<PromotionTO> promotions) {
        PromotionAdapter adapter = new PromotionAdapter(promotions, this);
        rvPromotion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}