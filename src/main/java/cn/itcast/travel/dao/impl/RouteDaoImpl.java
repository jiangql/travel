package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 获取路线的总数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int getTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid!=0){
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname!=null&&rname.length()!=0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql= sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 获取一页的路线信息
     * @param cid
     * @param start
     * @param rows
     * @param rname
     * @return
     */
    @Override
    public List<Route> getList(int cid, int start, int rows, String rname) {
        String sql = "select * from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid!=0){
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname!=null&&rname.length()!=0 &&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? ,?");
        sql= sb.toString();
        params.add(start);
        params.add(rows);
        List<Route> routeList = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        System.out.println(routeList);
        return routeList;
    }

    @Override
    public Route getOne(int rid) {
        String sql =" select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public void setCount(int count, int rid) {
        String sql = "update tab_route set count = ? where rid =?";
        template.update(sql,count,rid);
    }

    @Override
    public int getFavoriteTotalCount() {
        String sql ="select count(*) from tab_route where count > 0";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<Route> getRankList(int start, int rows) {
        String sql ="select * from tab_route where count > 0 order by count desc limit ? ,?";

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),start,rows);
    }

    @Override
    public List<Route> getPopular() {
        String sql ="select * from tab_route order by count desc limit 0,4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    @Override
    public List<Route> getNewest() {
        String sql="SELECT * from tab_route ORDER BY rdate desc limit 0,4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));



    }

    @Override
    public List<Route> getTravelPlaces(int cid) {
        String sql="SELECT * FROM tab_route WHERE cid = ? ORDER  BY  COUNT DESC limit 0,6";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid);
    }

}
