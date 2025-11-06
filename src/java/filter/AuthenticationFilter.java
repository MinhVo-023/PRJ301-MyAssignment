package filter;

/**
 *
 * @author Minh
 */

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AuthenticationFilter implements Filter {

    public void init(FilterConfig filterConfig) {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String query = req.getQueryString();
        // allow static resources and login pages
        boolean isStaticResource = uri.contains("/css/") || uri.contains("/js/");
        boolean isLoginPage = uri.endsWith("login.jsp");
        boolean isLoginAction = uri.endsWith("MainController") && query != null && query.equals("action=login");

        // cho qua 1 trong 3
        if (isStaticResource || isLoginPage || isLoginAction) {
            chain.doFilter(request, response);
            return;
        }

        //kiểm tra session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {}
}
