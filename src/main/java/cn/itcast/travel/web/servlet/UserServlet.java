package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private ResultInfo resultInfo = new ResultInfo();

    /**
     * 检测用户名可用性
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void exist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int i = userService.findUserName(username);
        Map<String, Object> map = new HashMap<>();
        if (i == 0) {
            map.put("flag", true);
            map.put("reg_msg", "可用的用户名");
        } else {
            map.put("flag", false);
            map.put("reg_msg", "用户名已存在");
        }
        writrValue(response, map);
    }

    /**
     * 实现注册
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server != null && !"".equals(checkcode_server) && checkcode_server.equalsIgnoreCase(check)) {
            try {
                Map<String, String[]> map = request.getParameterMap();
                User user = new User();
                BeanUtils.populate(user, map);
                userService.regUser(user);
                resultInfo.setFlag(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }
        writrValue(response, resultInfo);
    }

    /**
     * 激活账户
     * @param request
     * @param response
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        String Msg = null;
        if (code.length() == 32) {
            boolean flag = userService.activeUser(code);

            if (flag) {
                Msg = "激活成功，点击<a href='"+request.getContextPath()+"/login.html'>登录</a>";
            } else {
                Msg = "激活失败,请联系管理员";
            }
        } else {
            Msg = "错误的激活方式";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(Msg);
    }



    /**
     * 实现登录
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultInfo resultInfo = new ResultInfo();
        try {
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            BeanUtils.populate(user, map);
            User loginUser = userService.login(user);
            if (loginUser != null) {
                if ("Y".equals(loginUser.getStatus())) {
                    request.getSession().setAttribute("user", loginUser);
                    if ("on".equals(request.getParameter("autoLogin"))) {
                        Cookie cookie = new Cookie("autoLogin", user.getUsername() + "#" + user.getPassword());
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        response.addCookie(cookie);
                    }
                    resultInfo.setFlag(true);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("该用户未被激活");
                }
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户名或密码错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        writrValue(response, resultInfo);
    }

    /**
     * 查询登录状态
     * @param request
     * @param response
     * @throws IOException
     */
    public void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        writrValue(response,user);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("autoLogin",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
