package ua.epam.payments.payments.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LanguageFilter implements Filter {
    public static final String ENCODING = "UTF-8";
    public static final String LANG_UK = "uk";
    public static final String LANG_EN = "en";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(ENCODING);
        servletResponse.setCharacterEncoding(ENCODING);

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String language = (String) req.getSession().getAttribute("lang");

        if (language != null &&(language.equals(LANG_UK) || language.equals(LANG_EN))) {
            req.getSession().setAttribute("lang",language);
        } else req.getSession().setAttribute("lang", LANG_EN);


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
