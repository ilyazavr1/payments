package ua.epam.payments.payments.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for setting encoding and internalization.
 *
 *  @author Illia Smiian
 */
public class LanguageFilter implements Filter {

    private static final String LANG_UK = "uk";
    private static final String LANG_EN = "en";
    private static final String LANG_COOKIE_NAME = "lang";
    private static final String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig)  {


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
