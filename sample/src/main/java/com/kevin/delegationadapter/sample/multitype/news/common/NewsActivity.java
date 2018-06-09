package com.kevin.delegationadapter.sample.multitype.news.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.News;
import com.kevin.delegationadapter.sample.multitype.news.common.adapter.MorePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.common.adapter.OnePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.common.adapter.ThreePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.common.adapter.VideoAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

import java.util.List;

/**
 * NewsActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 17:00:41
 *         Major Function：<b>多类型Activity</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
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
        delegationAdapter.addDelegate(new OnePicAdapterDelegate());
        delegationAdapter.addDelegate(new ThreePicAdapterDelegate());
        delegationAdapter.addDelegate(new MorePicAdapterDelegate());
        delegationAdapter.addDelegate(new VideoAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String newsListStr = LocalFileUtils.getStringFormAsset(this, "news.json");
        List<News> newsList = new Gson().fromJson(newsListStr, new TypeToken<List<News>>() {
        }.getType());
        delegationAdapter.setDataItems(newsList);
    }
}
