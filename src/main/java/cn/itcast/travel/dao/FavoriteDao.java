package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    int getCount(int rid);

    int isFavorite(int i, int uid);

    void add(int integer, int uid);

    int getTotalCount(int uid);

    List<Route> getList(int start, int rows, int uid);

    int findCount(int rid);
}
