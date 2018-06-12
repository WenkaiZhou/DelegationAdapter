package com.kevin.delegationadapter.sample.multidataandtype.binding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter;
import com.kevin.delegationadapter.sample.bean.Goods;
import com.kevin.delegationadapter.sample.multidataandtype.bean.BlankParameters;
import com.kevin.delegationadapter.sample.multidataandtype.binding.adapter.ArticleAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.binding.adapter.BannerAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.binding.adapter.BlankAdapterDelegate;
import com.kevin.delegationadapter.sample.multidataandtype.binding.adapter.TagAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

/**
 * BindingGoodsActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-11 15:20:52
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BindingGoodsActivity extends AppCompatActivity {

    private GoodsActivityBinding mBinding;
    DelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = GoodsActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        // 设置LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        delegationAdapter = new SpanDelegationAdapter();
        // 添加委托Adapter
        delegationAdapter.addDelegate(new BannerAdapterDelegate());
        delegationAdapter.addDelegate(new BlankAdapterDelegate());
        delegationAdapter.addDelegate(new TagAdapterDelegate());
        delegationAdapter.addDelegate(new ArticleAdapterDelegate());
        mBinding.recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String goodsStr = LocalFileUtils.getStringFormAsset(this, "goods.json");
        Goods goods = new Gson().fromJson(goodsStr, Goods.class);
        // 设置数据
        delegationAdapter.addHeaderItem(goods.banner);
        delegationAdapter.addHeaderItem(new BlankParameters(13, 0xFFFFFFFF));
        delegationAdapter.addHeaderItems(goods.tags);
        delegationAdapter.addHeaderItem(new BlankParameters(13, 0xFFFFFFFF));
        delegationAdapter.addDataItems(goods.articles);
//        delegationAdapter.addHeaderItem(goods.banner);
    }

}
