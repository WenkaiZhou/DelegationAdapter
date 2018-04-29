package com.kevin.delegationadapter;

/**
 * ItemData
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 13:47:09
 *         Major Function：<b>For the same data object corresponding to multiple Delegate
 *         are indistinguishable.</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public class ItemData {

    private Object data;
    private String tag;

    public ItemData(Object data, String tag) {
        this.data = data;
        this.tag = tag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
