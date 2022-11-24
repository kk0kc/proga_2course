package val.shop.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import val.shop.dto.UserForm;
import val.shop.exceptions.ValidationException;
import val.shop.services.SignUpService;
import val.shop.services.validation.ErrorEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.signUpService = (SignUpService) context.getAttribute("signUpService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sign_up.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserForm form;
        try {
            form = UserForm.builder()
                    .name(request.getParameter("name"))
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .status("user")
                    .build();
        } catch (NumberFormatException e) {
            Set<ErrorEntity> errors = new HashSet<>();
            errors.add(ErrorEntity.INVALID_REQUEST);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("sign_up.jsp").forward(request, response);
            return;
        }

        try {
            signUpService.signUp(form);
        } catch (ValidationException e) {
            request.setAttribute("error", e.getEntity());
            request.getRequestDispatcher("sign_up.jsp").forward(request, response);
            return;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/user-login");
    }
}
