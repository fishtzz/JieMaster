package com.szmaster.jiemaster.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.szmaster.jiemaster.utils.Log;

/**
 * Created by jiangsiyu on 2018/1/3.
 * 通用RecyclerView.Adapter
 * 配合@{@link CommonHolder},@{@link RecyclerItem}使用
 * 子类头部使用注解@{@link HolderArray}或者@{@link CreateViewHolder}关联layoutResource,holder,viewType
 * 例如
 *
 * @HolderArray({@CreateViewHolder(resource=R.layout.xxx,holder=Xholder,viewType=1),...}) public class MyAdapter extends CommonRecyclerAdapter{...}
 */

public abstract class CommonRecyclerAdapter extends RecyclerView.Adapter<CommonHolder<RecyclerItem>> {

    protected Context mContext;
    protected ArrayList<RecyclerItem> mItems;

    public CommonRecyclerAdapter(Context context) {
        this.mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public CommonHolder<RecyclerItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(this.getClass(), mContext, parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (null != getItem(position)) {
            return getItem(position).getViewType();
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mItems.size() > 0) {
            return mItems.size();
        }
        return 0;
    }

    public RecyclerItem getItem(int position) {
        if (outOfBounds(position)) {
            Log.w("position 越界 -> getItem()失败");
            return null;
        } else {
            return mItems.get(position);
        }
    }

    @Override
    public void onBindViewHolder(CommonHolder<RecyclerItem> holder, int position) {
        if (null == holder) {
            Log.w("holder 为空 -> onBindViewHolder()失败");
        } else if (null == getItem(position)) {
            Log.w("item 为空 -> onBindViewHolder()失败");
        } else {
            holder.onBind(getItem(position));
        }
    }

    public void updateItems(List<? extends RecyclerItem> items) {
        mItems.clear();
        if (null != items && items.size() > 0) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addItems(List<? extends RecyclerItem> items) {
        if (null != items && items.size() > 0) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addItem(RecyclerItem item) {
        if (null != item) {
            mItems.add(item);
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (!outOfBounds(position)) {
            mItems.remove(position);
        }
        notifyDataSetChanged();
    }

    public void addItem(RecyclerItem item, int position) {
        if (null != item && !outOfBounds(position)) {
            mItems.add(item);
        }
        notifyDataSetChanged();
    }

    public ArrayList<RecyclerItem> getItems() {
        return mItems;
    }

    private boolean outOfBounds(int position) {
//        LogUtil.e(TAG, "position 越界");
        return position < 0 || position > mItems.size() - 1;
    }

    /**
     * 读取注解
     */
    protected CommonHolder createHolder(Class<? extends CommonRecyclerAdapter> adapter, Context context, ViewGroup parent, int viewType) {
        if (adapter.isAnnotationPresent(HolderArray.class)) {
            HolderArray holderArray = adapter.getAnnotation(HolderArray.class);
            CreateViewHolder[] holders = holderArray.value();
            for (CreateViewHolder holder : holders) {
                if (viewType == holder.viewType()) {
                    return createViewHolder(holder, context, parent);
                }
            }
        } else if (adapter.isAnnotationPresent(CreateViewHolder.class)) {
            CreateViewHolder holder = adapter.getAnnotation(CreateViewHolder.class);
            return createViewHolder(holder, context, parent);
        }
        return null;

    }

    /**
     * 通过反射生成ViewHolder实例
     *
     * @param holder
     * @param context
     * @param parent
     * @return
     */
    private CommonHolder createViewHolder(CreateViewHolder holder, Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(holder.resource(), parent, false);
        try {
            Class holderClz = Class.forName(holder.holder().getName());
            Constructor cons = holderClz.getConstructor(View.class, Context.class);
            return ((CommonHolder) cons.newInstance(view, context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
