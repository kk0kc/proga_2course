package pupu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@ContentServlet(resourcePath = "/WEB-INF/content_preview/preview_calc.html")
@WebServlet(name = "FirstDopServlet", urlPatterns = "/calc")
public class FirstDopServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/cat_calculator.jsp");
        view.forward(request, response);
    }
}
