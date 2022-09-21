package pupu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@ContentServlet(resourcePath = "/WEB-INF/content_preview/preview_photo.html")
@WebServlet(name = "SecondDopServlet", urlPatterns = "/photo")
public class SecondDopServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/cat_photo.jsp");
        view.forward(request, response);
    }
}
