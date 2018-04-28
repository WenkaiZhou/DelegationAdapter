package com.kevin.delegationadapter.sample.samedata.bean;

import java.util.List;

/**
 * Bill
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-28 15:43:32
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class Bill {

    public String title;
    public String waiter;
    public String cashier;
    public int ramadhin;
    public int guests;
    public String beginTime;
    public String endTime;
    public String duration;
    public List<Item> details;
    public String total;
    public String discounts;
    public String receivable;
    public String describe;

    public static class Item {
        public String name;
        public String count;
        public String price;
        public String subtotal;
    }

}
