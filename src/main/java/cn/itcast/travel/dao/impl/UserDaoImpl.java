package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @Override
    public int findUsername(String username) {
        String sql ="select COUNT(*) from tab_user where username = ?";
        Integer i = template.queryForObject(sql, Integer.class, username);
        return i;
    }

    /**
     * 保存注册用户的信息
     * @param user
     */
    @Override
    public void regUser(User user) {
        String  sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    @Override
    public int findUserBycode(String code) {
        String sql= " select count(*) from tab_user where code =?";
        return template.queryForObject(sql,Integer.class,code);
    }

    /**
     * 更改用户的激活状态
     * @param code
     */
    @Override
    public void updateUserStatus(String code) {
        String sql= "update tab_user set status='Y' where code=?";
        template.update(sql,code);
    }

    /**
     * 登录查询
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User login=null;
        try {
            String sql ="select * from tab_user where username=? and password =?";
            login = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
            return login;
        } catch (DataAccessException e) {

        }
        return login;
    }
}
