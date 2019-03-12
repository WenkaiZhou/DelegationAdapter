package com.kevin.delegationadapter.sample.pro.meishijie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.kevin.delegationadapter.extras.load.LoadDelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiChannelAdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiLoad2AdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiSancanAdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiZhuantiAdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiZhuantiTitleAdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.bean.Meishi;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

/**
 * MeishijieActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-13 10:59:25
 * Major Function：<b></b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MeishijieActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LoadDelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meishijie);
        initRecyclerView();
        initRefreshView();
        initData();
    }


    int count = 0;

    private void initRefreshView() {
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        delegationAdapter.reset();
                        count = 0;
                        initData();
                    }
                }, 2000);
            }
        });

        delegationAdapter.setOnLoadListener(new LoadDelegationAdapter.OnLoadListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        delegationAdapter.setLoading(false);
                        if (count == 2) {
                            delegationAdapter.setLoadFailed();
                        } else if (count == 5) {
                            delegationAdapter.setLoadCompleted();
                        } else {
                            String meishiStr = LocalFileUtils.getStringFormAsset(MeishijieActivity.this, "meishi.json");
                            Meishi meishi = new Gson().fromJson(meishiStr, Meishi.class);
                            delegationAdapter.addDataItems(meishi.zhuanti.items);
                        }
                        count++;
                    }
                }, 2000);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        delegationAdapter = new LoadDelegationAdapter();
        // 添加委托Adapter
        delegationAdapter
                .setLoadDelegate(new MeishiLoad2AdapterDelegate())
                .addDelegate(new MeishiChannelAdapterDelegate())
                .addDelegate(new MeishiSancanAdapterDelegate())
                .addDelegate(new MeishiZhuantiTitleAdapterDelegate())
                .addDelegate(new MeishiZhuantiAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String meishiStr = LocalFileUtils.getStringFormAsset(this, "meishi.json");
        Meishi meishi = new Gson().fromJson(meishiStr, Meishi.class);
        delegationAdapter.setDataItems(meishi.channel);
        delegationAdapter.addDataItem(meishi.sancan);
        delegationAdapter.addDataItem(meishi.zhuanti);
        delegationAdapter.addDataItems(meishi.zhuanti.items);
    }
}
