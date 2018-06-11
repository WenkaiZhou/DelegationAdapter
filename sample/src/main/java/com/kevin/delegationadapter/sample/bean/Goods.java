package com.kevin.delegationadapter.sample.bean;

import com.kevin.loopview.internal.LoopData;

import java.util.List;

/**
 * Goods
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-11 15:56:07
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class Goods {
    public LoopData banner;
    public List<TagItem> tags;
    public List<Article> articles;

    public static class TagItem {
        public int id;
        public String title;
        public String link;
        public String img;
    }

    public static class Article {
        public int id;
        public int type;
        public String title;
        public String pic;
        public String desc;
        public String time;
    }
}
