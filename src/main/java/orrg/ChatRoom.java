package orrg;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(urlPatterns = "/room", name = "ChatRoom")
public class ChatRoom extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        HttpSession session = req.getSession();
        String activeUser = (String) session.getAttribute("name");
        ServletContext servletContext = req.getServletContext();
        String activeRoom = (String) servletContext.getAttribute("room");
        MessageEntity entity = new MessageEntity(activeRoom,activeUser, message);
        SessionCreatedListener.messageEntities.add(entity);
        resp.sendRedirect("/room");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/chat_room.jsp").forward(req, resp);
    }

}
