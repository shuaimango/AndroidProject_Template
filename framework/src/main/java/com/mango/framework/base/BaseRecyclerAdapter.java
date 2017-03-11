package com.mango.framework.base;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.mango.framework.util.Util_collection;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public abstract class BaseRecyclerAdapter<DATA> extends RecyclerView.Adapter {
    protected ArrayMap<Integer, BaseRecyclerAdapter> mChildAdapters = new ArrayMap();
    protected Fragment mFragment;
    protected List<DATA> mDatas;
    private OnItemClickLitener onItemClickLitener;
    private OnItemLongClickLitener onItemLongClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public OnItemClickLitener getOnItemClickLitener() {
        return onItemClickLitener;
    }

    public void setOnItemLongClickLitener(OnItemLongClickLitener onItemLongClickLitener) {
        this.onItemLongClickLitener = onItemLongClickLitener;
    }

    public OnItemLongClickLitener getOnItemLongClickLitener() {
        return onItemLongClickLitener;
    }
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickLitener {
        boolean onItemLongClick(View view, int position);
    }
    public abstract class OnTouchClickListener implements View.OnTouchListener {
        protected float moveDistance;
        protected float preX, preY;

        public abstract void onClick(View v, MotionEvent event);

        public OnTouchClickListener(Context context) {
            moveDistance = ViewConfiguration.get(context).getScaledTouchSlop();
//        moveDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.getResources().getDisplayMetrics());
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    preX = event.getX();
                    preY = event.getY();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (Math.abs(event.getX() - preX) <= moveDistance && Math.abs(event.getY() - preY) <= moveDistance) {
                        onClick(v,event);
                    }
                    break;
            }
            return false;
        }
    }
    protected void initChildAdapters() {
    }

    public BaseRecyclerAdapter(Fragment fragment) {
        this.mFragment = fragment;
        initChildAdapters();
    }

    public BaseRecyclerAdapter(Fragment fragment, List<DATA> datas) {
        this(fragment);
        this.mDatas = datas;
    }


    public void setDataList(List<DATA> datas) {
        setDataList(datas, true);
    }

    public void setDataList(List<DATA> datas, boolean isNotifyDataSetChanged) {
        if (Util_collection.isEmpty(datas))
            return;
        this.mDatas = datas;
        if (isNotifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }
    public DATA getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mDatas.get(position);
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public Resources getResources() {
        return mFragment.getResources();
    }
    protected void onBindViewHolder(final RecyclerView.ViewHolder holder, final Object object){

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mChildAdapters.size()>0){
            BaseRecyclerAdapter baseRecyclerAdapter = mChildAdapters.get(viewType);
            return baseRecyclerAdapter.onCreateViewHolder(parent,viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder,  int position) {
        position = viewHolder.getLayoutPosition();
        // 如果设置了回调，则设置点击事件
        if (viewHolder != null) {
            if (onItemClickLitener != null) {
                viewHolder.itemView.setOnTouchListener(new OnTouchClickListener(viewHolder.itemView.getContext()) {
                    @Override
                    public void onClick(View v, MotionEvent event) {
                        onItemClickLitener.onItemClick(v, viewHolder.getLayoutPosition());
                    }
                });
            } else {
                viewHolder.itemView.setOnClickListener(null);
            }
            if (onItemLongClickLitener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onItemLongClickLitener.onItemLongClick(v, viewHolder.getLayoutPosition());
                    }
                });
            } else {
                viewHolder.itemView.setOnLongClickListener(null);
            }
        }
        if(mChildAdapters.size()>0){
            BaseRecyclerAdapter baseRecyclerAdapter =  mChildAdapters.get(getItemViewType(position));
            baseRecyclerAdapter.onBindViewHolder(viewHolder,getItem(position));
        }
    }

}
