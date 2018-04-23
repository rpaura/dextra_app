package com.dextra.rpaura.sandwichapp.view.impl;

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
import com.dextra.rpaura.sandwichapp.model.entity.Order;
import com.dextra.rpaura.sandwichapp.presenter.impl.OrderPresenter;
import com.dextra.rpaura.sandwichapp.view.ICartFragment;
import com.dextra.rpaura.sandwichapp.view.adapter.OrderAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CartFragment extends Fragment implements ICartFragment {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.fc_rv_orders)
    RecyclerView rvOrders;

    Unbinder unbinder;
    private OrderPresenter presenter;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        unbinder = ButterKnife.bind(this, view);

        rvOrders.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvOrders.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        presenter = new OrderPresenter(this);
        presenter.loadOrders();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onStop();
    }

    private void updateTableOrders(List<Order> orders) {
        OrderAdapter adapter = new OrderAdapter(orders);
        rvOrders.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showOrderOnIU(List<Order> orders) {
        updateTableOrders(orders);
        if (progress.isShown()) {
            hideProgress();
        }
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }
}