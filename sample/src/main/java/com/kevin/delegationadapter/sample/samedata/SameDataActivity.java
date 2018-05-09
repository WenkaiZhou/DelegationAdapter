package com.kevin.delegationadapter.sample.samedata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.ItemData;
import com.kevin.delegationadapter.sample.samedata.adapter.BillItemAdapterDelegate;
import com.kevin.delegationadapter.sample.samedata.adapter.ChargeInfoAdapterDelegate;
import com.kevin.delegationadapter.sample.samedata.adapter.ServiceInfoAdapterDelegate;
import com.kevin.delegationadapter.sample.samedata.bean.Bill;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * SameDataActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-28 15:36:58
 *         Major Function：<b>同一数据，不同样式</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class SameDataActivity extends AppCompatActivity {

    SameDataActivityBinding mBinding;
    DelegationAdapter mDelegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = SameDataActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDelegationAdapter = new DelegationAdapter();
        mDelegationAdapter.addDelegate(new ServiceInfoAdapterDelegate());
        mDelegationAdapter.addDelegate(new BillItemAdapterDelegate());
        mDelegationAdapter.addDelegate(new ChargeInfoAdapterDelegate());
        mBinding.recyclerView.setAdapter(mDelegationAdapter);
    }

    private void initData() {
        String newsListStr = LocalFileUtils.getStringFormAsset(this, "bill.json");
        Bill bill = new Gson().fromJson(newsListStr, Bill.class);

        List<Object> dataList = new ArrayList<>();
        dataList.add(new ItemData(bill, ServiceInfoAdapterDelegate.TAG));
        dataList.addAll(bill.details);
        dataList.add(new ItemData(bill, ChargeInfoAdapterDelegate.TAG));
        mDelegationAdapter.setDataItems(dataList);
    }
}
