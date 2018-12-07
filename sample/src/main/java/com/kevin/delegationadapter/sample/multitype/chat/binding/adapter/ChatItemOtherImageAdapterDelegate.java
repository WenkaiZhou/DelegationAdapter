package com.kevin.delegationadapter.sample.multitype.chat.binding.adapter;

import android.databinding.ViewDataBinding;

import com.android.databinding.library.baseAdapters.BR;
import com.kevin.delegationadapter.extras.binding.BindingAdapterDelegate;
import com.kevin.delegationadapter.sample.R;
import com.kevin.delegationadapter.sample.bean.Chat;

/**
 * ChatItemMyTextAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 10:51:45
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class ChatItemOtherImageAdapterDelegate extends BindingAdapterDelegate<Chat.TalkMsg> {

    @Override
    public boolean isForViewType(Chat.TalkMsg item, int position) {
        // 用户类型为1(自己)，条目类型2(图片)
        return item.user.type == 2 && item.type == 2;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_chat_other_image_binding;
    }

    @Override
    public void setVariable(ViewDataBinding binding, Chat.TalkMsg item, int position) {
        binding.setVariable(BR.model, item);
    }

}
