package ua.epam.payments.payments.web.filters;

import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LanguageFilterTest {

    private LanguageFilter languageFilter = new LanguageFilter();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);

    private final ServletRequest request = mock(ServletRequest.class);
    private final ServletResponse response = mock(ServletResponse.class);


    private final FilterChain filterChain = mock(FilterChain.class);
    private final HttpSession session = mock(HttpSession.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);


    public static final String LANG_UK = "uk";
    public static final String LANG_EN = "en";
    public static final String LANG_COOKIE_NAME = "lang";
    private static String encoding = "UTF-8";

    @Test
    public void whenLocaleNotNull() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("sessionLocale")).thenReturn(LANG_UK);

        languageFilter.doFilter(req, resp, filterChain);

        verify(req.getSession(), times(1)).setAttribute(LANG_COOKIE_NAME,LANG_UK);
    }


}