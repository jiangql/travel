package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        int i =favoriteDao.isFavorite(Integer.parseInt(rid),uid);
        return i==0;
    }

    @Override
    public void addFavorite(String ridstr, int uid) {
        int rid =Integer.parseInt(ridstr);
        favoriteDao.add(rid,uid);
        int count =favoriteDao.findCount(rid);
        routeDao.setCount(count+1,rid);
    }

    @Override
    public PageBean<Route> getRoute(int rows, int currentPage, int uid) {
        PageBean<Route> pb = new PageBean<>();
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);

        int totalCount =favoriteDao.getTotalCount(uid);
        pb.setTotalCount(totalCount);

        int totalPage =(int)Math.ceil(totalCount*1.0/rows);
        pb.setTotalPage(totalPage);

        int start =(currentPage-1)*rows;
        List<Route> list = favoriteDao.getList(start,rows,uid);
        pb.setList(list);

        return pb;
    }
}
