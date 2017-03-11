package com.mango.framework.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.mango.framework.util.Util_skipPage;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseFragment extends Fragment implements IPageInit {
    public String TAG;
    private ViewStub viewStub_titlebar;
    public Bundle extras;
    public BusinessLogic businessLogic;

    public boolean isRegisterEventBus() {
        return false;
    }

    public boolean isMultipleClick() {
        if (businessLogic != null)
            return businessLogic.isMultipleClick;
        return false;
    }

    @Override
    public void onDestroy() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        extras = getArguments();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view);
//        viewStub_titlebar = (ViewStub) view.findViewById(R.id.viewStub_titlebar);
//        if (viewStub_titlebar != null) {
//                viewStub_titlebar.setLayoutResource(R.layout.titlebar_common);
//                viewStub_titlebar.inflate();
//                viewStub_titlebar.setVisibility(View.VISIBLE);
//        }
        return view;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables(savedInstanceState);
        if (businessLogic == null) {
            businessLogic = new BusinessLogic(this);
        }
        setViews(view);
        loadData();
        //在fragment布局文件里的根布局加上android:clickable="true",解决多层Fragment重叠，让底层的Fragment不响应点击\滑动事件
        //            view.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;，但fragment嵌套fragment会出现问题。http://blog.csdn.net/spt_dream/article/details/50855943
//                }
//            });
    }

    public void showLoadingDialog() {
        showLoadingDialog(true);
    }

    public void showLoadingDialog(boolean isCancel) {
    }


    public void dismissLoadingDialog() {
    }


    public void onBackPressed() {
        Util_skipPage.fragmentBack(getActivity());
    }


}
