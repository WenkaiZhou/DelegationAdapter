package com.kevin.delegationadapter.sample.multidataandtype;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.multidataandtype.adapter.DoubleAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.adapter.FloatAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.adapter.IntegerAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.adapter.StringAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiDataAndTypeActivity
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-28 11:12:26
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MultiDataAndTypeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DelegationAdapter mDelegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_data_and_type);
        initRecyclerView();
        initData();
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
        mDelegationAdapter.addDelegate(new StringAdapterDelegate());
        mDelegationAdapter.addDelegate(new IntegerAdapterDelegate());
        mDelegationAdapter.addDelegate(new FloatAdapterDelegate());
        mDelegationAdapter.addDelegate(new DoubleAdapterDelegate());
        // 添加委托Adapter
        mRecyclerView.setAdapter(mDelegationAdapter);
    }

    private void initData() {
        List<Object> dataList = new ArrayList<>();
        dataList.add("今天天气怎么样？");  // 添加一个String类型数据
        dataList.add("大晴天，有点热。");  // 添加一个String类型数据
        dataList.add("温度多少度呢？");    // 添加一个String类型数据
        dataList.add(29);                // 添加一个int类型数据
        dataList.add("具体是多少？");      // 添加一个String类型数据
        dataList.add(29.5F);             // 添加一个Float类型数据
        dataList.add(29.527921364978D);  // 添加一个Double类型数据
        mDelegationAdapter.setDataItems(dataList);
    }
}
