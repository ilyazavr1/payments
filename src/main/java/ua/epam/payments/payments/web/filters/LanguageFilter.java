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
/* public static final String ENCODING = "UTF-8";
     servletRequest.setCharacterEncoding("UTF-8");
       servletResponse.setCharacterEncoding(ENCODING);
        servletResponse.setContentType("text/html;charset=UTF-8");
        servletRequest.setCharacterEncoding(encoding);*/
     /*   if (language != null &&(language.equals(LANG_UK) || language.equals(LANG_EN))) {
            req.setAttribute("lang", language);

        } else {
            req.setAttribute("lang", LANG_UK);
        }*/
// System.out.println(req.getSession().getAttribute("lang"));

/*req.setAttribute("test", "123");
        if (req.getParameter("sessionLocale") == null) {
            req.getSession().setAttribute(LANG_COOKIE_NAME, LANG_UK);
        } else if(req.getParameter("sessionLocale").equalsIgnoreCase(LANG_EN)){
            req.getSession().setAttribute(LANG_COOKIE_NAME, LANG_EN);
        } else req.getSession().setAttribute(LANG_COOKIE_NAME, LANG_UK);t
       // System.out.println(req.getSession().getAttribute("lang"));*/
