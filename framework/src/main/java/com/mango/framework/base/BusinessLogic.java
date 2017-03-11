package com.mango.framework.base;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/3/2.
 */
public class BusinessLogic implements View.OnClickListener, View.OnLongClickListener, BaseRecyclerAdapter.OnItemClickLitener {
    protected Fragment fragment;
    protected Activity activity;
    protected View  clickView;
    protected boolean isMultipleClick;
    protected long clickTime;
    public BusinessLogic(Activity activity) {
        this.activity = activity;
    }
    public BusinessLogic(Fragment fragment) {
        this(fragment.getActivity());
        this.fragment = fragment;
    }

    public BusinessLogic() {
    }
    public  Activity getActivity(){
        return activity;
    }
    @Override
    public void onClick(View v) {
        //判断是否快速双击同一控件，避免重复执行业务逻辑
        isMultipleClick = v == clickView && System.currentTimeMillis() - clickTime < 600;//是否是同一控件的快速双击事件
        clickView = v;
        clickTime = System.currentTimeMillis();
        if(isMultipleClick) {
            Toast.makeText(activity, "请稍等再试", Toast.LENGTH_SHORT).show();
            return;
        }
        //点击事件
        int viewId = v.getId();
//        if (viewId == R.id.titlebar_left) {
//            Util_skipPage.fragmentBack(activity);
//        } else if (viewId == R.id.titlebar_middle) {
//
//        } else if (viewId == R.id.titlebar_right) {
//
//        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onItemClick(View v, int position) {

    }

}
