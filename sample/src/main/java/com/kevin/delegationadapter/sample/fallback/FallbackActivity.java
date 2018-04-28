package com.kevin.delegationadapter.sample.fallback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MultiTypeActivity
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-27 17:00:41
 *         Major Function：<b>多类型Activity</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class FallbackActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DelegationAdapter mDelegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallback);
        initRecyclerView();
        initData();

        findViewById(R.id.btn_conversion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更换位置
                mDelegationAdapter.moveDataItem(2, 8);
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
        // 添加委托Adapter
        mDelegationAdapter.addDelegate(new TextAdapterDelegate());
        // 添加兜底的委托Adapter
        mDelegationAdapter.setFallbackDelegate(new FallbackAdapterDelegate());
        mRecyclerView.setAdapter(mDelegationAdapter);
    }

    private void initData() {
        // 除去4之外，其余都是String类型
        Object[] datas = {"1", "2", "3", 4, "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        List<Object> dataList = Arrays.asList(datas);
        mDelegationAdapter.setDataItems(new ArrayList(dataList));
    }
}
