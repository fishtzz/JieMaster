package com.szmaster.jiemaster.widget.recyclerview;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiangsiyu on 2018/1/3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateViewHolder {

    int resource() default -1;

    int viewType() default 0;

    Class<? extends CommonHolder> holder();
}
