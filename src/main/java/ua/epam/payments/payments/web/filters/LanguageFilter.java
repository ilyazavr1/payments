package ua.epam.payments.payments.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LanguageFilter implements Filter {

    public static final String LANG_UK = "uk";
    public static final String LANG_EN = "en";
    private String encoding = "utf-8";

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
    /* public static final String ENCODING = "UTF-8";
     servletRequest.setCharacterEncoding("UTF-8");
       servletResponse.setCharacterEncoding(ENCODING);
        servletResponse.setContentType("text/html;charset=UTF-8");
        servletRequest.setCharacterEncoding(encoding);*/

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String language = (String) req.getAttribute("lang");



        if (language != null &&(language.equals(LANG_UK) || language.equals(LANG_EN))) {
            req.setAttribute("lang", language);
           // req.getSession().setAttribute("lang",language);
        } else req.setAttribute("lang", LANG_UK);

        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
