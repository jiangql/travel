package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;


public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int getCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public int isFavorite(int rid, int uid) {
        String sql = "select count(*) from tab_favorite where uid =? and rid =?";
        return template.queryForObject(sql, Integer.class, uid, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        Date date = new Date();
        template.update(sql, rid, date, uid);
    }

    @Override
    public int getTotalCount(int uid) {
        String sql = "select count(*) from tab_favorite where uid =?";
        Integer count = template.queryForObject(sql, Integer.class, uid);
        return count;
    }

    @Override
    public List<Route> getList(int start, int rows, int uid) {
        String sql = "SELECT * FROM tab_route WHERE rid IN (SELECT rid FROM tab_favorite WHERE uid = ?) LIMIT ?,?";
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid, start, rows);
        return list;
    }

    @Override
    public int findCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }
}
