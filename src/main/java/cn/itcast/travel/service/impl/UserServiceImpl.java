package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public int findUserName(String username) {
        return dao.findUsername(username);
    }

    @Override
    public void regUser(User user) {
        //设置用户的初始状态和唯一激活码
        user.setStatus("N");
        String code = UuidUtil.getUuid();
        user.setCode(code);
        dao.regUser(user);
        //发送激活邮件
        String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
    }

    @Override
    public boolean activeUser(String code) {
        int i = dao.findUserBycode(code);
        if ( i== 1) {
            dao.updateUserStatus(code);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return dao.login(user);
    }
}
