package com.kevin.delegationadapter.sample.multitype.chat.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.extras.load.LoadDelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Chat;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemMyImageAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemMyTextAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.common.adapter.ChatItemOtherTextAdapterDelegate;
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

    int count = 0;

    private void initRecyclerView() {
        recyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        //        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        delegationAdapter = new LoadDelegationAdapter();
        // 添加委托Adapter
        delegationAdapter.addDelegate(new ChatItemMyImageAdapterDelegate());
        delegationAdapter.addDelegate(new ChatItemMyTextAdapterDelegate());
        delegationAdapter.addDelegate(new ChatItemOtherTextAdapterDelegate());
        delegationAdapter.setLoadDelegate(new ChatLoadAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);


        delegationAdapter.setOnLoadListener(new LoadDelegationAdapter.OnLoadListener() {

            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count == 2) {
                            delegationAdapter.showLoadFailed();
                        } else if (count == 5) {
                            delegationAdapter.showLoadComplete();
                        } else {
                            delegationAdapter.setLoading(false);
                            String chatStr = LocalFileUtils.getStringFormAsset(ChatActivity.this, "chat.json");
                            Chat chat = new Gson().fromJson(chatStr, Chat.class);
                            delegationAdapter.addDataItems(chat.msgs);
                        }

                        count++;
                    }
                }, 2000);
                Toast.makeText(ChatActivity.this, "加载更多 " + count, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        String chatStr = LocalFileUtils.getStringFormAsset(this, "chat.json");
        Chat chat = new Gson().fromJson(chatStr, Chat.class);
        // 设置数据
        delegationAdapter.setDataItems(chat.msgs);
    }

}
