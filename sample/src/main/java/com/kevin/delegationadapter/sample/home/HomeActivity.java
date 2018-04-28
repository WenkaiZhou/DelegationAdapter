package com.kevin.delegationadapter.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.binding.BindingMultiTypeActivity;
import com.kevin.delegationadapter.sample.fallback.FallbackActivity;
import com.kevin.delegationadapter.sample.footer.FooterActivity;
import com.kevin.delegationadapter.sample.header.HeaderActivity;
import com.kevin.delegationadapter.sample.multidataandtype.MultiDataAndTypeActivity;
import com.kevin.delegationadapter.sample.multitype.MultiTypeActivity;

import java.util.Arrays;
import java.util.List;

/**
 * HomeActivity
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-27 16:12:00
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        delegationAdapter.addDelegate(new HomeAdapterDelegate(this));
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String[] titles = {
                "同一数据类型多种样式",
                "同一数据类型多种样式(dataBinding实现)",
                "不同数据类型多种样式",
                "带头部数据的不同数据类型多样式",
                "带尾部数据的不同数据类型多样式"
        };
        List<String> titleList = Arrays.asList(titles);
        delegationAdapter.setDataItems(titleList);
    }

    public void onItemClick(View v, int position, String item) {
        switch (position) {
            case 0:
                startActivity(new Intent(HomeActivity.this, MultiTypeActivity.class));
                break;
            case 1:
                startActivity(new Intent(HomeActivity.this, BindingMultiTypeActivity.class));
                break;
            case 2:
                startActivity(new Intent(HomeActivity.this, MultiDataAndTypeActivity.class));
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this, HeaderActivity.class));
                break;
            case 4:
                startActivity(new Intent(HomeActivity.this, FooterActivity.class));
                break;
            case 5:
                startActivity(new Intent(HomeActivity.this, FallbackActivity.class));
                break;
        }
    }
}
