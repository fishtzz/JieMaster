package com.szmaster.jiemaster.ui;

import android.content.Context;

import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.widget.recyclerview.CommonRecyclerAdapter;
import com.szmaster.jiemaster.widget.recyclerview.CreateViewHolder;

@CreateViewHolder(resource = R.layout.holder_grid, holder = GridHolder.class, viewType = 0)
public class GridAdapter extends CommonRecyclerAdapter {
    public GridAdapter(Context context) {
        super(context);
    }
}
