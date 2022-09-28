package orrg;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/room", name = "ChatRoom")
public class ChatRoom extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        ServletContext servletContext = req.getServletContext();
        String activeRoom = (String) servletContext.getAttribute("room");

        MessageEntity entity = new MessageEntity(activeRoom, message);
        SessionCreatedListener.messageEntities.add(entity);

        resp.sendRedirect("/room");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServletContext servletContext = req.getServletContext();
//        if (servletContext.getAttribute("activeRoom") == null) {
//            req.getRequestDispatcher("/WEB-INF/not_found.jsp").forward(req, resp);
//        }
        String activeRoom = (String) servletContext.getAttribute("room");
        req.getRequestDispatcher("/WEB-INF/chat_room.jsp").forward(req, resp);
    }

}
