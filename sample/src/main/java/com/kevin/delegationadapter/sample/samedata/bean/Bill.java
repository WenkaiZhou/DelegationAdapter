package com.kevin.delegationadapter.sample.samedata.bean;

import java.util.List;

/**
 * Bill
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-28 15:43:32
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class Bill {

    public String title = "";           // 标题
    public String waiter = "";          // 服务员
    public String cashier = "";         // 收银员
    public int ramadhin = 0;            // 桌号
    public int guests = 0;              // 客人数
    public String beginTime = "";       // 开台时间
    public String endTime = "";         // 结账时间
    public String duration = "";        // 用餐时长
    public List<Item> details = null;   // 用餐详情
    public String total = "";           // 合计
    public String discounts = "";       // 优惠
    public String receivable = "";      // 应收
    public String describe = "";        // 描述信息

    public static class Item {
        public String name = "";    // 名称
        public String count = "";   // 数量
        public String price = "";   // 单价
        public String subtotal = "";// 小计
    }

}
