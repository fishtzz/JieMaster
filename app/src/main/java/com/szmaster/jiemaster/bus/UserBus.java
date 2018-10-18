package com.szmaster.jiemaster.bus;

import java.util.HashSet;
import java.util.Set;

import com.szmaster.jiemaster.model.User;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public class UserBus {

    private static UserBus instance;
    private static Set<IUser> sIUserSet;
    private boolean isLogin;
    private String token;
    private User mUser;

    public static UserBus getInstance() {
        if (null == instance) {
            instance = new UserBus();
            sIUserSet = new HashSet<>();
        }
        return instance;
    }

    public void login(User user) {
        this.mUser = user;
        this.isLogin = true;
        this.token = user.getToken();
        for (IUser iUser : sIUserSet) {
            iUser.onLogin(user);
        }
    }

    public void logout() {
        this.mUser = null;
        this.isLogin = false;
        this.token = "";
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
        return token;
    }

    public User getUser() {
        return mUser;
    }
}
