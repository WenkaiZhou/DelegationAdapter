package com.kevin.delegationadapter.sample.multitype.chat.binding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.bean.Chat;
import com.kevin.delegationadapter.sample.multitype.chat.binding.adapter.ChatItemMyImageAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.binding.adapter.ChatItemMyTextAdapterDelegate;
import com.kevin.delegationadapter.sample.multitype.chat.binding.adapter.ChatItemOtherTextAdapterDelegate;
import com.kevin.delegationadapter.sample.util.LocalFileUtils;

/**
 * BindingChatActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 10:46:41
 *         Major Function：<b>聊天界面</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class BindingChatActivity extends AppCompatActivity {

    DelegationAdapter delegationAdapter;

    private BindingChatBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = BindingChatBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        delegationAdapter = new DelegationAdapter();
        // 添加委托Adapter
        delegationAdapter.addDelegate(new ChatItemOtherTextAdapterDelegate());
        delegationAdapter.addDelegate(new ChatItemMyTextAdapterDelegate());
        delegationAdapter.addDelegate(new ChatItemMyImageAdapterDelegate());
        mBinding.recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String chatStr = LocalFileUtils.getStringFormAsset(this, "chat.json");
        Chat chat = new Gson().fromJson(chatStr, Chat.class);
        // 设置数据
        delegationAdapter.setDataItems(chat.msgs);
    }
}
