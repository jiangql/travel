package cn.itcast.travel.service;


import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {

    PageBean<Route> getPageBean(int cid, int currentPage, int rows, String rname);

    Route getRoute(String ridstr);

    PageBean<Route> getCountRank(int rows, int currentPage);

    List<Route> getPopular();

    List<Route> getNewestRoute();


    List<Route> getTravelPlaces(int cid);
}
