/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import java.util.List;

/**
 * Products
 *
 * @author zhouwenkai@baidu.com, Created on 2019-04-14 14:42:33
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class Products {

    public static final int TYPE_NORMAL_PRODUCT = 7;
    public static final int TYPE_SECOND_BANNER = 9;
    public static final int TYPE_BIG_PRODUCT_SHELF = 26;

    public List<CellItem> cellList;

    public static class CellItem {
        public int cellType;
        public SecondBanner secondBanner;
        public String bgImage;
        public NormalProduct normalProduct;
    }

    public static class SecondBanner {
        public String path;
    }

    public static class NormalProduct {
        public String image;
        public String name;
        public String subtitle;
        public int price;
        public String cartImage;
    }
}
