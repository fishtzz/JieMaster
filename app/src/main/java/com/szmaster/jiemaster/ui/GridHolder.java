package com.szmaster.jiemaster.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szmaster.jiemaster.BrowserActivity;
import com.szmaster.jiemaster.GlideApp;
import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.model.ReportItem;
import com.szmaster.jiemaster.utils.CommonUtil;
import com.szmaster.jiemaster.widget.recyclerview.CommonHolder;

public class GridHolder extends CommonHolder<ReportItem> {

    private ImageView img;
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView tvLabel1;
    private TextView tvLabel2;
    private View root;
    private Context mContext;

    public GridHolder(View itemView, Context context) {
        super(itemView, context);
        mContext = context;
        root = itemView;
        img = itemView.findViewById(R.id.img);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDesc = itemView.findViewById(R.id.tv_desc);
        tvLabel1 = itemView.findViewById(R.id.tv_label1);
        tvLabel2 = itemView.findViewById(R.id.tv_label2);
        int width = CommonUtil.getWindowWidth(context) / 2;
        int height = width * 5 / 11;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        root.setLayoutParams(params);
    }

    @Override
    public void onBind(final ReportItem item) {
        GlideApp.with(mContext).load(item.getImg()).into(img);
        tvTitle.setText(item.getTitle());
        tvDesc.setText(item.getDesc());
        tvLabel1.setText(item.getLabel1());
        tvLabel2.setText(item.getLabel2());
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.launcher(mContext,
                        item.getUrl(),
                        item.getTitle());
            }
        });
    }
}
