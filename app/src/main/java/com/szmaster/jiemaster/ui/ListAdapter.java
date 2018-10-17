package com.szmaster.jiemaster.ui;

import android.content.Context;

import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.widget.recyclerview.CommonRecyclerAdapter;
import com.szmaster.jiemaster.widget.recyclerview.CreateViewHolder;

@CreateViewHolder(resource = R.layout.holder_list, holder = ListHolder.class, viewType = 0)
public class ListAdapter extends CommonRecyclerAdapter {
    public ListAdapter(Context context) {
        super(context);
    }
}
