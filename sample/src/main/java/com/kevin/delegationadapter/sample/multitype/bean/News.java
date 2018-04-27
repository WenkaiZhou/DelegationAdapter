package com.kevin.delegationadapter.sample.multitype.bean;

import java.util.List;

/**
 * News
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-21 20:51:10
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class News {
    public int type = 0; // 0:一张图片；1:三张图片；2:多张图片；3:视频类型
    public List<String> imgUrls = null;
    public String content = "";
    public String count = "";
    public String duration = "";
    public String source = "";
    public String time = "";
    public String link = "";
}
