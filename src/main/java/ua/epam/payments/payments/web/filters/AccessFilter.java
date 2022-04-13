package ua.epam.payments.payments.web.filters;

import ua.epam.payments.payments.model.entity.Role;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@WebFilter("/*")
public class AccessFilter implements Filter {

    public static final List<String> ADMIN_PATHS = Path.ADMIN_PATHS;
    public static final List<String> CLIENT_PATHS = Path.CLIENT_PATHS;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            if (req.getServletPath().equals(Path.LOGIN_PATH) || req.getServletPath().equals(Path.REGISTRATION_PATH)) {
                servletRequest.getRequestDispatcher(req.getServletPath()).forward(servletRequest, servletResponse);
                return;
            }
            servletRequest.getRequestDispatcher(Path.LOGIN_PATH).forward(servletRequest, servletResponse);
            return;
        }
/*

        if (user.getBlocked()) {
            req.getSession().invalidate();
            servletRequest.setAttribute(Constants.USER_IS_BLOCKED, Constants.USER_IS_BLOCKED);
            servletRequest.getRequestDispatcher(Path.LOGIN_PATH).forward(servletRequest, servletResponse);
            return;
        }*/

        if (ADMIN_PATHS.contains(req.getServletPath())) {
            if (req.getSession().getAttribute("userRole").equals(Role.CLIENT.name())) {
                res.sendRedirect(Path.CARDS_PATH);
                return;
            }
        }
        if (CLIENT_PATHS.contains(req.getServletPath())) {
            if (req.getSession().getAttribute("userRole").equals(Role.ADMINISTRATOR.name())) {
                res.sendRedirect(Path.ADMIN_ALL_USERS_PATH);
                return;
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }
}
