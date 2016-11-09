package com.risenb.ykj.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Fengyi on 2016/8/11.
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    protected List<T> mData;
    protected Context mContext;
    protected final Object mLock = new Object();


    public BaseRecycleAdapter(Context context) {
        this.mData = new ArrayList<>();
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    abstract public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return OnCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }


    /**
     * 获取数据项
     */
    public T getItem(int position) {
        return mData.get(position);
    }


    /**
     * 获取数量
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }
    /**
     * 获得数据下标
     */
    public int getItemIndex(T object){
        if (object != null) {
            return mData.indexOf(object);
        }else{
            return 0;
        }

    }
    /**
     * 获取所有数据
     */
    public List<T> getData() {
        return mData;
    }


    /**
     * 刷新数据
     */
    public void freshData(List<T> newData) {
        if (newData != null) {
            synchronized (mLock) {
                this.mData = newData;
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加数据
     */
    public void add(T object) {
        if (object != null) {
            synchronized (mLock) {
                mData.add(object);
            }
            notifyItemInserted(getItemCount() - 1);
        }
    }

    /**
     * 批量添加数据
     */
    public void addAll(Collection<? extends T> collection) {

        if (collection != null && collection.size() != 0) {
            synchronized (mLock) {
                mData.addAll(collection);
            }

            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
        }
    }


    /**
     * 插入数据
     */
    public void insert(T object, int index) {
        if (object != null) {
            synchronized (mLock) {
                mData.add(index, object);
            }
            notifyItemInserted(getItemCount() - 1);
        }
    }


    /**
     * 批量插入数据
     */
    public void insertAll(Collection<? extends T> collection, int index) {

        if (collection != null && collection.size() != 0) {
            synchronized (mLock) {
                mData.addAll(index, collection);
            }
            notifyItemRangeInserted(index, collection.size());
        }
    }

    /**
     * 删除一条数据
     */
    public void remove(T object) {
        int position = mData.indexOf(object);
        synchronized (mLock) {
            if (mData.remove(object)) {
             //   notifyItemInserted(position - 1);
                notifyItemRemoved(position);
            }
        }
    }
    /**
     * 更改单条数据
     */

    public void update(T object){
        int position = mData.indexOf(object);
        synchronized (mLock){
            mData.set(position,object);
        }
        notifyItemChanged(position);
    }
    /**
     * 清空数据
     */
    public void clear() {
        int count = mData.size();
        synchronized (mLock) {
            mData.clear();
        }
        notifyItemRangeRemoved(0, count);
    }

    /**
     * 数据排序
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            Collections.sort(mData, comparator);
        }
        notifyDataSetChanged();
    }



}
