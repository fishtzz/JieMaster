package com.szmaster.jiemaster.bus;

import java.util.HashSet;
import java.util.Set;

import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.User;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public class UserBus {

    private static UserBus instance;
    private static Set<IUser> sIUserSet;
    private boolean isLogin;

    public static UserBus getInstance() {
        if (null == instance) {
            instance = new UserBus();
            sIUserSet = new HashSet<>();
        }
        return instance;
    }

    public void login(User user) {
        this.isLogin = true;
        PreferenceImp.login(user);
        for (IUser iUser : sIUserSet) {
            iUser.onLogin(user);
        }
    }

    public void logout() {
        PreferenceImp.logout();
        this.isLogin = false;
        for (IUser iUser : sIUserSet) {
            iUser.onLogout();
        }
    }

    public void registerIUser(IUser iUser) {
        if (null != iUser) {
            sIUserSet.add(iUser);
        }
    }

    public void unregisterIUser(IUser iUser) {
        if (null != iUser && sIUserSet.contains(iUser)) {
            sIUserSet.remove(iUser);
        }
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getToken() {
        return PreferenceImp.getToken();
    }

    public User getUser() {
        return PreferenceImp.getUserCache();
    }
}
