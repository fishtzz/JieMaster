package com.szmaster.jiemaster.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szmaster.jiemaster.GlideApp;
import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.UserSettingActivity;
import com.szmaster.jiemaster.bus.UserBus;

public class UserFragment extends Fragment implements View.OnClickListener {
    private View root;
    private ImageView avatar;
    private TextView tvUsername;
    private TextView tvId;
    private View setting;
    private View help;
    private View about;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == root) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        avatar = root.findViewById(R.id.avatar);
        tvUsername = root.findViewById(R.id.tv_username);
        tvId = root.findViewById(R.id.tv_id);
        setting = root.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        help = root.findViewById(R.id.help);
        help.setOnClickListener(this);
        about = root.findViewById(R.id.about);
        about.setOnClickListener(this);
        if (null == UserBus.getInstance().getUser()) {
            UserBus.getInstance().logout();
        } else {
            GlideApp.with(getActivity()).load(UserBus.getInstance().getUser().getUserImg()).into(avatar);
            tvUsername.setText(UserBus.getInstance().getUser().getUserName());
            tvId.setText(getString(R.string.id, UserBus.getInstance().getUser().getUserId()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                startActivity(new Intent(getActivity(), UserSettingActivity.class));
                break;
            case R.id.about:
                break;
            case R.id.help:
                break;
            default:
                break;
        }
    }
}
