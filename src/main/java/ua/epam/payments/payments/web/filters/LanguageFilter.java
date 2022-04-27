package ua.epam.payments.payments.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LanguageFilter implements Filter {

    public static final String LANG_UK = "uk";
    public static final String LANG_EN = "en";
    public static final String LANG_COOKIE_NAME = "lang";
    private static String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam =
                filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String language = req.getParameter("sessionLocale");


        if (language != null && (language.equals(LANG_UK) || language.equals(LANG_EN))) {
            req.getSession().setAttribute(LANG_COOKIE_NAME, language);
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
