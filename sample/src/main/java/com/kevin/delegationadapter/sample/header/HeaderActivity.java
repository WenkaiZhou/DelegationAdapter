package com.kevin.delegationadapter.sample.header;

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
import com.kevin.delegationadapter.sample.util.LocalFileUtils;
import com.kevin.loopview.internal.LoopData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * HeaderActivity
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-28 13:24:27
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HeaderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;
    private int addHeaderCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        initRecyclerView();
        initData();

        findViewById(R.id.btn_add_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegationAdapter.addHeaderItem("添加的头部数据：" + (++addHeaderCount));
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // 设置Adapter
        delegationAdapter = new DelegationAdapter();
        delegationAdapter.addDelegate(new TextAdapterDelegate());
        delegationAdapter.addDelegate(new BannerDelegateAdapter());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String loopStr = LocalFileUtils.getStringFormAsset(this, "loop.json");
        LoopData loopData = new Gson().fromJson(loopStr, LoopData.class);
        delegationAdapter.addHeaderItem(loopData);
        String[] datas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        List<String> dataList = Arrays.asList(datas);
        delegationAdapter.setDataItems(new ArrayList(dataList));
    }
}
