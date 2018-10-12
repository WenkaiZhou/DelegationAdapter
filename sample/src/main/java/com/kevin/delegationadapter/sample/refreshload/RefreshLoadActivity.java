package com.kevin.delegationadapter.sample.refreshload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.fallback.TextAdapterDelegate;
import com.kevin.delegationadapter.sample.header.BannerAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;
import com.kevin.loopview.internal.LoopData;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RefreshLoadActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-10-12 13:55:07
 * Major Function：<b>刷新加载Activity</b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class RefreshLoadActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DelegationAdapter mDelegationAdapter;

    private int mLastId; // 刷新加载最后一个Id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshload);

        initRefreshLayout();
        initRecyclerView();
        initData();
    }

    private void initRefreshLayout() {
        RefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

                // 更新内容
                mLastId = 0;
                List<String> stringList = getData();
                mDelegationAdapter.setDataItems(stringList);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

                // 添加内容
                List<String> stringList = getData();
                mDelegationAdapter.addDataItems(stringList);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = this.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        mDelegationAdapter = new DelegationAdapter();
        // 向Adapter中注册委托Adapter
        mDelegationAdapter.addDelegate(new BannerAdapterDelegate());
        mDelegationAdapter.addDelegate(new TextAdapterDelegate());
        mRecyclerView.setAdapter(mDelegationAdapter);
    }

    private void initData() {
        String loopStr = LocalFileUtils.getStringFormAsset(this, "banner.json");
        LoopData loopData = new Gson().fromJson(loopStr, LoopData.class);
        mDelegationAdapter.addHeaderItem(loopData); // 添加Banner头部数据

        mLastId = 0;
        List<String> stringList = getData();
        mDelegationAdapter.setDataItems(stringList);
    }


    /**
     * 假装获取条目数据
     *
     * @return
     */
    private List<String> getData() {
        List<String> stringList = new ArrayList<>();
        int num = new Random().nextInt(10) + 5;
        for (int i = 0; i < num; i++) {
            stringList.add(" 条目：" + mLastId++);
        }
        return stringList;
    }
}
