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
import com.dextra.rpaura.sandwichapp.presenter.impl.LunchPresenter;
import com.dextra.rpaura.sandwichapp.view.ISandwichFragment;
import com.dextra.rpaura.sandwichapp.view.adapter.ClickList;
import com.dextra.rpaura.sandwichapp.view.adapter.SandwichAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SandwichFragment extends Fragment implements ISandwichFragment, ClickList {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.f_rv_sandwich)
    RecyclerView rvLunch;

    Unbinder unbinder;
    private LunchPresenter presenter;

    public static SandwichFragment newInstance() {
        return new SandwichFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandwich, container, false);

        unbinder = ButterKnife.bind(this, view);

        rvLunch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvLunch.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        presenter = new LunchPresenter(this);
        presenter.loadLunchs();
        return view;
    }

    @Override
    public void showLunchOnIU(List<Sandwich> lunchs) {
        updateTableLunch(lunchs);
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

    private void updateTableLunch(List<Sandwich> lunchs) {
        SandwichAdapter adapter = new SandwichAdapter(lunchs, this);
        rvLunch.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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