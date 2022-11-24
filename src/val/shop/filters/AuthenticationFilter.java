package val.shop.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class AuthenticationFilter implements Filter {
//    private SignInService signInService;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext context = filterConfig.getServletContext();
//        this.signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // преобразуем запросы к нужному виду
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().startsWith("/includes")) {
            filterChain.doFilter(request, response);
            return;
        }

        // берем сессию у запроса
        // берем только существующую, если сессии не было - то вернет null
        HttpSession session = request.getSession(false);
        // флаг, аутентифицирован ли пользователь
        boolean isAuthenticated = false;
        // существует ли сессия вообще?
        boolean sessionExists = session != null;
        // идет ли запрос на страницу входа или регистрации?
        boolean isRequestOnAuthPage = request.getRequestURI().equals("/sign-up") ||
                request.getRequestURI().equals("/user-login");
        boolean isRequestOnHomePage = request.getRequestURI().equals("/");
        boolean isRequestErrorLog = request.getRequestURI().equals("/error-log");

        // если сессия есть
        if (sessionExists) {
            // проверим, есть ли атрибует user?
            isAuthenticated = session.getAttribute("auth") != null;
        }

        // если авторизован и запрашивает не открытую страницу или если не авторизован и запрашивает открытую
        if (isAuthenticated && !isRequestOnAuthPage || !isAuthenticated && isRequestOnAuthPage) {
            // отдаем ему то, что он хочет
            filterChain.doFilter(request, response);
        } else if (isAuthenticated && isRequestOnAuthPage) {
            // пользователь аутенцифицирован и запрашивает страницу входа
            // - отдаем ему профиль
            response.sendRedirect("/");
        } else if (!isAuthenticated && isRequestOnHomePage){
            // если пользователь не аутенцицицирован и запрашивает другие страницы
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