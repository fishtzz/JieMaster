package com.szmaster.jiemaster.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/**
 * Created by jiangsiyu on 2018/1/2.
 */
public abstract class CommonHolder<T extends RecyclerItem> extends ViewHolder {

    protected Context mContext;

    public CommonHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
    }

    public abstract void onBind(T item);

}
