package val.shop.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().startsWith("/includes")) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession(false);
        boolean isAuthenticated = false;
        boolean sessionExists = session != null;
        boolean isRequestOnAuthPage = request.getRequestURI().equals("/sign-up") ||
                request.getRequestURI().equals("/user-login");
        boolean isRequestOnHomePage = request.getRequestURI().equals("/");
        boolean isRequestErrorLog = request.getRequestURI().equals("/error-log");

        if (sessionExists) {
            isAuthenticated = session.getAttribute("auth") != null;
        }

        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
            filterChain.doFilter(request, response);
        } else if (isAuthenticated && isRequestOnAuthPage) {
            response.sendRedirect("/");
        } else if (!isAuthenticated && isRequestOnHomePage){
            filterChain.doFilter(request, response);
        }
        else if (!isAuthenticated && isRequestErrorLog){
            filterChain.doFilter(request, response);
        }
        else {
            response.sendRedirect("/user-login");
        }
    }

    @Override
    public void destroy() {}
}