package com.kevin.delegationadapter.sample.fallback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;

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

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        initRecyclerView();
        initData();
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
        // 添加委托Adapter
        delegationAdapter.addDelegate(new TextAdapterDelegate());
        // 添加兜底的委托Adapter
        delegationAdapter.setFallbackDelegate(new FallbackAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        // 除去4之外，其余都是String类型
        Object[] datas = {"1", "2", "3", 4, "5", "6", "7", "8", "9"};
        List<Object> dataList = Arrays.asList(datas);
        delegationAdapter.setDataItems(dataList);
    }
}
