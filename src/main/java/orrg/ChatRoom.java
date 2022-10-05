package orrg;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = "/room", name = "ChatRoom")
public class ChatRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        HttpSession session = req.getSession();
        String activeUser = (String) session.getAttribute("name");
        ServletContext servletContext = req.getServletContext();
        String activeRoom = (String) session.getAttribute("room");
        MessageEntity entity = new MessageEntity(activeRoom,activeUser, message);
        SessionCreatedListener.messageEntities.add(entity);
        req.getRequestDispatcher("/WEB-INF/chat_room.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/chat_room.jsp").forward(req, resp);
    }

}
