package com.kevin.delegationadapter.sample.multitype.news.binding;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.bean.News;
import com.kevin.delegationadapter.sample.binding.BindingMultiTypeActivityBinding;
import com.kevin.delegationadapter.sample.multitype.news.binding.adapter.MorePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.binding.adapter.OnePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.binding.adapter.ThreePicAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.news.binding.adapter.VideoAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

import java.util.List;

/**
 * BindingMultiTypeActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 18:39:08
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BindingMultiTypeActivity extends AppCompatActivity {

    BindingMultiTypeActivityBinding mBinding;
    DelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = BindingMultiTypeActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        delegationAdapter = new DelegationAdapter();
        delegationAdapter.addDelegate(new OnePicAdapterDelegate());
        delegationAdapter.addDelegate(new ThreePicAdapterDelegate());
        delegationAdapter.addDelegate(new MorePicAdapterDelegate());
        delegationAdapter.addDelegate(new VideoAdapterDelegate());
        mBinding.recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String newsListStr = LocalFileUtils.getStringFormAsset(this, "news.json");
        List<News> newsList = new Gson().fromJson(newsListStr, new TypeToken<List<News>>() {
        }.getType());
        delegationAdapter.setDataItems(newsList);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
