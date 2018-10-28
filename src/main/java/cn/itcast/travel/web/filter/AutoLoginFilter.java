package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User existuser = (User) session.getAttribute("user");
        if (existuser != null) {
            chain.doFilter(request, resp);
        } else {
            Cookie[] cookies = request.getCookies();
            Cookie autoLogin = CookieUtils.findCookie(cookies, "autoLogin");
            if (autoLogin == null) {
                chain.doFilter(request, resp);
            } else {
                String value = autoLogin.getValue();
                String username = value.split("#")[0];
                String password = value.split("#")[1];

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                UserService userService = new UserServiceImpl();
                User login = userService.login(user);
                if (login != null) {
                    session.setAttribute("user", login);
                    chain.doFilter(request, resp);
                } else {
                    chain.doFilter(req, resp);
                }
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }


}
