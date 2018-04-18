package com.kevin.delegationadapter.sample;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;


import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.ItemData;
import com.kevin.delegationadapter.sample.bean.ImageBean;
import com.kevin.delegationadapter.sample.bean.ImageTextBean;
import com.kevin.delegationadapter.sample.bean.TextBean;
import com.kevin.delegationadapter.sample.delegate.ImageAndTextViewHolder;
import com.kevin.delegationadapter.sample.delegate.ImageViewViewHolder;
import com.kevin.delegationadapter.sample.delegate.LeftTextAdapterDelegate;
import com.kevin.delegationadapter.sample.delegate.RightTextAdapterDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    MainActivityBinding mBinding;
    DelegationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mAdapter.removeDataItem(3);
            }
        }, 5000);
    }

    protected void initViews() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DelegationAdapter();
        // LeftTextViewHolder和RightTextViewHolder具有相同的实体模型
        mAdapter.addDelegate(new LeftTextAdapterDelegate());
        mAdapter.addDelegate(new RightTextAdapterDelegate());
//        mAdapter.addDelegate(new ImageAndTextViewHolder(), "LeftImage");
//        mAdapter.addDelegate(new ImageAndTextViewHolder());
        mAdapter.addDelegate(new ImageAndTextViewHolder());
        mAdapter.addDelegate(new ImageViewViewHolder());
//        mAdapter.register(new BannerViewHolder());
        mBinding.recyclerView.setAdapter(mAdapter);
        List<Object> list = new ArrayList<>();

//        String homeIndexStr = LocalFileUtil.getStringFormAsset(this, "home_index.json");
//        HomeIndex homeIndex = new Gson().fromJson(homeIndexStr, HomeIndex.class);
//        mAdapter.addHeaderItem(homeIndex.loopData);
//        mAdapter.addFooterItem(homeIndex.loopData);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            list.add(new TextBean("AAA" + i, random.nextBoolean()));
            list.add(new TextBean("BBB" + i, random.nextBoolean()));
            list.add(new ImageBean(R.mipmap.img1));
//            list.add(new ImageTextBean(R.mipmap.img2, "CCC" + i));
            list.add(new ItemData(new ImageTextBean(R.mipmap.img2, "CCC" + i), "LeftImage"));
        }
        mAdapter.setDataItems(list);
    }

    private void loadData() {
//        String homeIndexStr = LocalFileUtil.getStringFormAsset(this, "home_index.json");
//        HomeIndex mHomeIndex = new Gson().fromJson(homeIndexStr, HomeIndex.class);

//        List<DelegateAdapter.Adapter> mAdapters = new AdapterBuild()
//                .addAdapter(new BannerAdapter(this, mHomeIndex.loopData))
//                .addAdapter(new BroadcastAdapter(this, mHomeIndex.broadcast))
//                .addAdapter(new DividerLineAdapter(mHomeIndex.dividerLine))
//                .addAdapter(new HotPointAdapter(this, mHomeIndex.hotPoint, 5))
//                .addAdapter(new SpecialAdapter(this, mHomeIndex.special))
//                .addAdapter(new RecommendTitleAdapter(mHomeIndex.recommendTitle))
//                .addAdapter(new RecommendAdapter(mHomeIndex.recommend, 2))
//                .build();
//        adapters.clear();
//        adapters.addAll(mAdapters);
    }

    // ------------------------------
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}
