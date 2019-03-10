/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.meishijie;

import com.kevin.delegationadapter.extras.load.LoadAdapterDelegate;
import com.kevin.delegationadapter.sample.R;

/**
 * ChatLoadAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-09 12:55:19
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class MeishiLoadAdapterDelegate extends LoadAdapterDelegate {

    @Override
    public int getLoadingLayoutRes() {
        return R.layout.layout_load_more_footer;
    }

    @Override
    public int getNoMoreLayoutRes() {
        return R.layout.layout_no_more_footer;
    }

    @Override
    public int getLoadFailedLayoutRes() {
        return R.layout.layout_load_fail_footer;
    }
}
