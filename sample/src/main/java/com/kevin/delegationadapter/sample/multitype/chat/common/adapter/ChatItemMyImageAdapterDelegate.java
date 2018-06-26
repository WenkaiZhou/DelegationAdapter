package com.kevin.delegationadapter.sample.multitype.chat.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.extras.ClickableAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Chat;

/**
 * ChatItemMyImageAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 17:30:06
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ChatItemMyImageAdapterDelegate extends ClickableAdapterDelegate<Chat.TalkMsg, ChatItemMyImageAdapterDelegate.ViewHolder> {

    @Override
    protected boolean isForViewType(Chat.TalkMsg item, int position) {
        // 用户类型为1(自己)，条目类型1(图片)
        return item.user.type == 1 && item.type == 2;
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_my_image, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onItemClick(View view, Chat.TalkMsg item, int position) {
        Toast.makeText(view.getContext(), "position = " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, Chat.TalkMsg item) {
        Glide.with(holder.itemView.getContext()).load(item.user.avatar).into(holder.ivAvatar);
        Glide.with(holder.itemView.getContext()).load(item.pic).asBitmap().override(600, 600).into(holder.ivPic);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        ImageView ivPic;

        public ViewHolder(View view) {
            super(view);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            ivPic = view.findViewById(R.id.iv_pic);
        }
    }
}
