package com.kevin.delegationadapter.sample.footer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.fallback.TextAdapterDelegate;
import com.kevin.delegationadapter.sample.header.BannerDelegateAdapter;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;
import com.kevin.loopview.internal.LoopData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FooterActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-28 13:54:05
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class FooterActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DelegationAdapter mDelegationAdapter;
    int addFooterCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        initRecyclerView();
        initData();

        findViewById(R.id.btn_add_footer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加尾部数据
                mDelegationAdapter.addFooterItem("添加的尾部数据：" + (++addFooterCount));
                // 滚动到底部
                mRecyclerView.scrollToPosition(mDelegationAdapter.getItemCount() - 1);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        // 设置Adapter
        mDelegationAdapter = new DelegationAdapter();
        mDelegationAdapter.addDelegate(new TextAdapterDelegate());
        mDelegationAdapter.addDelegate(new BannerDelegateAdapter());
        mRecyclerView.setAdapter(mDelegationAdapter);
    }

    private void initData() {
        String loopStr = LocalFileUtils.getStringFormAsset(this, "loop.json");
        LoopData loopData = new Gson().fromJson(loopStr, LoopData.class);
        mDelegationAdapter.addFooterItem(loopData);
        String[] datas = {"1", "2", "3", "4", "5"};
        List<String> dataList = Arrays.asList(datas);
        mDelegationAdapter.setDataItems(new ArrayList(dataList));
    }
}
