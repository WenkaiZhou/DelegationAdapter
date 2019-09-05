package com.kevin.delegationadapter.sample.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.delegationadapter.DelegationAdapter;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.fallback.FallbackActivity;
import com.kevin.delegationadapter.sample.footer.FooterActivity;
import com.kevin.delegationadapter.sample.header.HeaderActivity;
import com.kevin.delegationadapter.sample.multidataandtype.binding.BindingGoodsActivity;
import com.kevin.delegationadapter.sample.multitype.chat.binding.BindingChatActivity;
import com.kevin.delegationadapter.sample.multitype.chat.common.ChatActivity;
import com.kevin.delegationadapter.sample.multitype.news.binding.BindingNewsActivity;
import com.kevin.delegationadapter.sample.multitype.news.common.NewsActivity;
import com.kevin.delegationadapter.sample.pro.meishijie.MeishijieActivity;
import com.kevin.delegationadapter.sample.pro.missfresh.MissFreshActivity;
import com.kevin.delegationadapter.sample.refreshload.RefreshLoadActivity;
import com.kevin.delegationadapter.sample.samedata.SameDataActivity;

import java.util.Arrays;
import java.util.List;

/**
 * HomeActivity
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 16:12:00
 * Major Function：<b></b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class HomeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DelegationAdapter mDelegationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        mRecyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        // 设置Adapter
        mDelegationAdapter = new DelegationAdapter();
        // 向Adapter中注册委托Adapter
        mDelegationAdapter.addDelegate(new HomeAdapterDelegate(this));
        mRecyclerView.setAdapter(mDelegationAdapter);

        // 添加拖动排序
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HomeItemDragCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    List<String> titleList = Arrays.asList(
            "同一类型多种样式(新闻)",
            "同一类型多种样式(新闻-dataBinding实现)",
            "同一类型多种样式(聊天)",
            "同一类型多种样式(聊天-dataBinding实现)",
            "不同数据类型多种样式",
            "同一数据多种类型",
            "带头部数据的不同数据类型多样式",
            "带尾部数据的不同数据类型多样式",
            "带兜底的委托Adapter(未注册委托时的处理)",
            "美食杰",
            "每日优鲜",
            "刷新加载");

    private void initData() {
        mDelegationAdapter.setDataItems(titleList);
    }

    public void onItemClick(View v, int position, String item) {
        int index = titleList.indexOf(item);
        switch (index) {
            case 0:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, BindingNewsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, BindingChatActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, BindingGoodsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, SameDataActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, HeaderActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, FooterActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, FallbackActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, MeishijieActivity.class));
                break;
            case 10:
                startActivity(new Intent(this, MissFreshActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, RefreshLoadActivity.class));
                break;
            default:
                // Can't reach;
                break;
        }
    }
}
