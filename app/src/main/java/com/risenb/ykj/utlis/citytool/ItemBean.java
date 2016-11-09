package com.risenb.ykj.utlis.citytool;

import java.util.List;

/**
 * Created by LL on 2016/9/14.
 */
public class ItemBean implements Cloneable {
    private long baseId;
    private String showValue;
    private long realValue;
    private long pid;
    private List<ItemBean> children;

    public long getBaseId() {
        return baseId;
    }

    public void setBaseId(long baseId) {
        this.baseId = baseId;
    }

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    public long getRealValue() {
        return realValue;
    }

    public void setRealValue(long realValue) {
        this.realValue = realValue;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<ItemBean> getChildren() {
        return children;
    }

    public void setChildren(List<ItemBean> children) {
        this.children = children;
    }


    @Override
    public ItemBean clone() {
        try {
            return (ItemBean) super.clone();
        } catch (CloneNotSupportedException e) {

        }
        return null;
    }
}
