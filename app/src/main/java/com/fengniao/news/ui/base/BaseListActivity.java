package com.fengniao.news.ui.base;

import android.os.Bundle;

public abstract class BaseListActivity extends BaseActivity implements FNAdapter.ViewProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
