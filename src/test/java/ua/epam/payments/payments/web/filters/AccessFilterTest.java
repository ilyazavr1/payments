package ua.epam.payments.payments.web.filters;

import org.junit.Test;
import ua.epam.payments.payments.model.entity.Role;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.web.Path;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class AccessFilterTest {

    private final AccessFilter accessFilter = new AccessFilter();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);
    private final HttpSession session = mock(HttpSession.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    private final static User USER_NULL = null;
    private final static User USER_NOT_NULL = new User();

    @Test
    public void accessClientToUsersPath() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(USER_NOT_NULL);
        when(req.getSession().getAttribute("userRole")).thenReturn(Role.CLIENT.name());
        when(req.getServletPath()).thenReturn(Path.PAYMENTS_PATH);

        accessFilter.doFilter(req, resp, filterChain);

        verify(filterChain, times(1)).doFilter(req, resp);
        verify(resp, times(0)).sendRedirect(Path.CARDS_PATH);
    }

    @Test
    public void accessDeniedClientToAdministratorPath() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(USER_NOT_NULL);
        when(req.getSession().getAttribute("userRole")).thenReturn(Role.CLIENT.name());
        when(req.getServletPath()).thenReturn(Path.ADMIN_ALL_USERS_PATH);

        accessFilter.doFilter(req, resp, filterChain);

        verify(resp, times(1)).sendRedirect(Path.CARDS_PATH);
    }

    @Test
    public void accessAdministratorToAdministratorPath() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(USER_NOT_NULL);
        when(req.getSession().getAttribute("userRole")).thenReturn(Role.ADMINISTRATOR.name());
        when(req.getServletPath()).thenReturn(Path.ADMIN_ALL_USERS_PATH);

        accessFilter.doFilter(req, resp, filterChain);

        verify(filterChain, times(1)).doFilter(req, resp);
        verify(resp, times(0)).sendRedirect(Path.CARDS_PATH);
    }

    @Test
    public void accessDeniedAdministratorToClientPath() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(USER_NOT_NULL);
        when(req.getSession().getAttribute("userRole")).thenReturn(Role.ADMINISTRATOR.name());
        when(req.getServletPath()).thenReturn(Path.CARDS_PATH);

        accessFilter.doFilter(req, resp, filterChain);

        verify(resp, times(1)).sendRedirect(Path.ADMIN_ALL_USERS_PATH);
    }

    @Test
    public void accessWhenUserNull() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getServletPath()).thenReturn(Path.LOGIN_PATH);

        when(req.getRequestDispatcher(req.getServletPath())).thenReturn(requestDispatcher);
        when(req.getSession().getAttribute("user")).thenReturn(null);

        accessFilter.doFilter(req, resp, filterChain);

        verify(req.getRequestDispatcher(req.getServletPath()), times(1)).forward(req,resp);
    }

}