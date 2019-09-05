/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

public class MissFreshActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ProductAdapter mDelegationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miss_fresh);
        initRecyclerView();

        initData();
    }

    private void initData() {
        String productListStr = LocalFileUtils.getStringFormAsset(this, "products.json");
        Products products = new Gson().fromJson(productListStr, Products.class);
        mDelegationAdapter.setDataItems(products.cellList);
    }

    private void initRecyclerView() {
        mRecyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        final LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        mDelegationAdapter = new ProductAdapter();
        mRecyclerView.setAdapter(mDelegationAdapter);

        BackgroundItemDecoration itemDecoration = new BackgroundItemDecoration();
        itemDecoration.setDecorationFactory(new BackgroundItemDecoration.DecorationFactory() {
            @Override
            public BackgroundItemDecoration.Decoration createDecoration(int position) {
                return mDelegationAdapter == null ? null : mDelegationAdapter.getDecoration(position);
            }
        });
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
    }

}
