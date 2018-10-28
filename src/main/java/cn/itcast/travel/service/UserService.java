package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    int findUserName(String username);

    void regUser(User user);

    User login(User user);

    boolean activeUser(String code);
}
