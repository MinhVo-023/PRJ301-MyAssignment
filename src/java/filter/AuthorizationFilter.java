package filter;

/**
 *
 * @author Minh
 */

import dao.FeatureDAO;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    private FeatureDAO featureDAO = new FeatureDAO();

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String query = req.getQueryString();
        //cho phép login
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/");
        boolean isLoginPage = uri.endsWith("login.jsp");
        boolean isLoginAction = uri.endsWith("MainController") && query != null && query.equals("action=login");

        //cho qua 1 trong 3
        if (isStaticResource || isLoginPage || isLoginAction) {
            chain.doFilter(request, response);
            return;
        }
        //kiểm tra session
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp"); return;
        }
        User u = (User) session.getAttribute("user");
        if (u == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp"); return;
        }

        try {
            String action = req.getParameter("action");
            if (action == null) {
                action = "home"; 
            }
            String servletPath = req.getServletPath();
            String featureToTest = servletPath + "?action=" + action;
            System.out.println("--- AUTH_FILTER_DEBUG: Checking permission for UserID: " + u.getId() + " on Feature: [" + featureToTest + "]");
            boolean ok = featureDAO.userHasAccess(u.getId(), req.getQueryString() == null ? uri : uri + "?" + req.getQueryString());
            if (!ok) {
                res.sendRedirect(req.getContextPath() + "/error403.jsp");
                return;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {}
}

