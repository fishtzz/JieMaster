package com.szmaster.jiemaster.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

/**
 * Created by jiangsiyu on 2018/1/3.
 */

public class AnnotateUtils {

    public static CommonHolder createHolder(Class<? extends CommonRecyclerAdapter> adapter, Context context, ViewGroup parent, int viewType) {
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

    private static CommonHolder createViewHolder(CreateViewHolder holder, Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(holder.resource(), parent, false);
        try {
            return ((CommonHolder) newInstance(holder.holder().getName(), new Object[]{view}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object newInstance(String className, Object[] args) throws Exception {
        Class newOneClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Constructor cons = newOneClass.getConstructor(argsClass);
        return cons.newInstance(args);
    }

}
