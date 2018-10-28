package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {
    boolean isFavorite(String rid, int uid);

    void addFavorite(String rid, int uid);

    PageBean<Route> getRoute(int rows, int currentPage, int uid);
}
