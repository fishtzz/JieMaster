package com.szmaster.jiemaster.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szmaster.jiemaster.BrowserActivity;
import com.szmaster.jiemaster.GlideApp;
import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.model.ReportActivity;
import com.szmaster.jiemaster.utils.CommonUtil;
import com.szmaster.jiemaster.widget.recyclerview.CommonHolder;

public class ListHolder extends CommonHolder<ReportActivity> {
    private Context mContext;
    private View root;
    private TextView tvTitle;
    private TextView tvDesc;
    private ImageView img;

    public ListHolder(View itemView, Context context) {
        super(itemView, context);
        root = itemView;
        mContext = context;
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDesc = itemView.findViewById(R.id.tv_desc);
        img = itemView.findViewById(R.id.img);
        int width = CommonUtil.getWindowWidth(context);
        int height = width / 4;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        img.setLayoutParams(params);
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(width, height * 5 / 4);
        root.setLayoutParams(params1);
    }

    @Override
    public void onBind(final ReportActivity item) {
        tvTitle.setText(item.getTitle());
        tvDesc.setText(item.getDesc());
        GlideApp.with(mContext).load(item.getImg()).into(img);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.launcher(mContext, item.getUrl(), item.getTitle());
            }
        });
    }
}
