package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void getPb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidstr = request.getParameter("cid");
        String currentPagestr = request.getParameter("currentPage");
        String rowsstr = request.getParameter("rows");
        String rname = request.getParameter("rname");
        /*rname = new String(rname.getBytes("iso-8859-1"), "utf-8");*/
        int cid = 0;
        if (cidstr != null && cidstr.length() != 0 && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }
        int currentPage = 1;
        if (currentPagestr != null && !"".equals(currentPagestr)) {
            currentPage = Integer.parseInt(currentPagestr);
        }
        int rows = 5;
        if (rowsstr != null && !"".equals(rowsstr)) {
            rows = Integer.parseInt(rowsstr);
        }

        PageBean<Route> pb = routeService.getPageBean(cid, currentPage, rows, rname);

        writrValue(response, pb);
    }

    public void getRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridstr = request.getParameter("rid");
        Route route = routeService.getRoute(ridstr);
        writrValue(response, route);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);

        writrValue(response, flag);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            return;
        } else {
            uid = user.getUid();
        }
        favoriteService.addFavorite(rid, uid);
    }

    public void selectFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user == null) {
            return;
        } else {
            uid = user.getUid();
        }

        String currentPageStr = request.getParameter("currentPage");
        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() != 0 && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        String rowsStr = request.getParameter("rows");
        int rows = 12;
        if (rowsStr != null && rowsStr.length() != 0 && !"null".equals(rowsStr)) {
            rows = Integer.parseInt(rowsStr);
        }

        PageBean<Route> pb = favoriteService.getRoute(rows, currentPage, uid);
        writrValue(response, pb);
    }

    public void selectFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rnameStr = request.getParameter("rname");
        String minStr = request.getParameter("min");
        String maxStr = request.getParameter("max");


        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        int currentPage = 1;
        if (currentPageStr != null && !"".equals(currentPageStr) && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        int rows = 12;
        if (rowsStr != null && !"".equals(rowsStr) && !"null".equals(rowsStr)) {
            rows = Integer.parseInt(rowsStr);
        }
        PageBean<Route> pb = routeService.getCountRank(rows, currentPage);
        writrValue(response, pb);
    }

    public void selectPopularRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> list = routeService.getPopular();
        writrValue(response,list);
    }
    public void list_newestRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> list =routeService.getNewestRoute();
        writrValue(response,list);
    }
    public void list_travelPlace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
        int cid = Integer.parseInt(cidStr);
        List<Route> list=routeService.getTravelPlaces(cid);
        writrValue(response,list);
    }
}