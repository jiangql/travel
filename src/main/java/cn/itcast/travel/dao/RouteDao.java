package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int getTotalCount(int cid, String rname);

    List<Route> getList(int cid, int start, int rows, String rname);

    Route getOne(int rid);

    void setCount(int i, int rid);

    List<Route> getRankList(int start, int rows);

    int getFavoriteTotalCount();

    List<Route> getPopular();

    List<Route> getNewest();

    List<Route> getTravelPlaces(int cid);
}
