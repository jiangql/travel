package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    int findUsername(String username);

    void regUser(User user);


    User login(User user);

    int findUserBycode(String code);

    void updateUserStatus(String code);
}
