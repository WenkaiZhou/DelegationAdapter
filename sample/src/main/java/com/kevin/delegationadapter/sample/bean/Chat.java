package com.kevin.delegationadapter.sample.bean;

import java.util.List;

/**
 * Chat
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-09 10:30:25
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class Chat {

    public List<TalkMsg> msgs;

    public static class TalkMsg {
        public int type;
        public User user;
        public String text;
        public String pic;
    }

    public static class User {
        public int type;
        public String avatar;
    }
}
