package com.kevin.delegationadapter.sample.multitype.chat.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Chat;

/**
 * ChatItemMyTextAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 17:38:37
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ChatItemMyTextAdapterDelegate extends AdapterDelegate<Chat.TalkMsg, ChatItemMyTextAdapterDelegate.ViewHolder> {

    @Override
    public boolean isForViewType(Chat.TalkMsg item, int position) {
        // 用户类型为1(自己)，条目类型1(文本)
        return item.user.type == 1 && item.type == 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_my_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Chat.TalkMsg item) {
        Glide.with(holder.itemView.getContext()).load(item.user.avatar).into(holder.ivAvatar);
        holder.tvContent.setText(item.text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            tvContent = view.findViewById(R.id.tv_content);
        }
    }
}
