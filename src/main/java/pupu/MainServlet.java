package pupu;

import java.awt.image.BufferedImage;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.imageio.ImageIO;

@WebServlet(name = "MainServlet", urlPatterns = "/hello")
public class MainServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/my.jsp");
        view.forward(request, response);
    }
}