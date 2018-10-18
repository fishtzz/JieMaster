package com.szmaster.jiemaster.bus;

import com.szmaster.jiemaster.model.User;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public interface IUser {

    void onLogin(User user);

    void onLogout();

}
