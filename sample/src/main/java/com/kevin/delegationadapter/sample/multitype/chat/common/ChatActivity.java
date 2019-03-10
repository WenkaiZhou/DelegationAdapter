package com.kevin.delegationadapter.sample.multitype.chat.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.kevin.delegationadapter.extras.load.LoadDelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Chat;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemMyImageAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemMyTextAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemOtherTextAdapterDelegate;
import com.kevin.delegationadapter.sample.pro.meishijie.adapter.MeishiLoadAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

/**
 * ChatActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 17:26:40
 * Major Function：<b>聊天界面</b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LoadDelegationAdapter delegationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        recyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        //        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        delegationAdapter = new LoadDelegationAdapter();
        // 添加委托Adapter
        delegationAdapter
                .setLoadDelegate(new MeishiLoadAdapterDelegate())
                .addDelegate(new ChatItemMyImageAdapterDelegate())
                .addDelegate(new ChatItemMyTextAdapterDelegate())
                .addDelegate(new ChatItemOtherTextAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String chatStr = LocalFileUtils.getStringFormAsset(this, "chat.json");
        Chat chat = new Gson().fromJson(chatStr, Chat.class);
        // 设置数据
        delegationAdapter.setDataItems(chat.msgs);
    }

}
