package com.mango.framework.base;

import android.os.Bundle;
import android.view.View;

/**
 */

public interface IPageInit {
    int getLayoutId();

    void initViews(View parentView);

    void initVariables(Bundle savedInstanceState);

    void setViews(View parentView);

    void loadData();
}
