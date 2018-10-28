package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> getPageBean(int cid, int currentPage, int rows, String rname) {
        PageBean pb = new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        int totalCount=dao.getTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        int totalPage = (int) Math.ceil(totalCount*1.0/rows);
        pb.setTotalPage(totalPage);

        int start = (currentPage-1)*rows;
        List<Route> list = dao.getList(cid,start,rows,rname);
        pb.setList(list);

        return pb;
    }

    @Override
    public Route getRoute(String ridstr) {
        int rid = Integer.parseInt(ridstr);
        Route route =dao.getOne(rid);

        List<RouteImg> routeImgList = routeImgDao.getImgs(rid);
        route.setRouteImgList(routeImgList);

        Seller seller = sellerDao.getSeller(route.getSid());
        route.setSeller(seller);

        int count =favoriteDao.getCount(rid);
        route.setCount(count);

        return route;
    }

    @Override
    public PageBean<Route> getCountRank(int rows, int currentPage) {
        PageBean<Route> pb = new PageBean<>();
        pb.setRows(rows);
        pb.setCurrentPage(currentPage);

        int totalCount=dao.getFavoriteTotalCount();
        pb.setTotalCount(totalCount);

        int totalPage = (int) Math.ceil(totalCount*1.0/rows);
        pb.setTotalPage(totalPage);

        int start = (currentPage-1)*rows;
        List<Route> list = dao.getRankList(start,rows);
        pb.setList(list);
        return pb;
    }

    @Override
    public List<Route> getPopular() {
        List<Route> list=dao.getPopular();
        return list;
    }

    @Override
    public List<Route> getNewestRoute() {
        List<Route> list = dao.getNewest();
        return list;
    }

    @Override
    public List<Route> getTravelPlaces(int cid) {
        List<Route> list = dao.getTravelPlaces(cid);
        return list;
    }
}
