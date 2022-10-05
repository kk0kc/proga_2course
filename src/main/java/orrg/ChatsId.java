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
import java.util.Objects;

@WebServlet(name = "MainServlet", urlPatterns = "/id")
public class ChatsId extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String dataUser = req.getParameter("user");
        session.setAttribute("name", dataUser);
        ArrayList<String> arr2 = SessionCreatedListener.users;
        ServletContext servletContext = req.getServletContext();
        servletContext.setAttribute("user", dataUser);
        if (!SessionCreatedListener.users.contains(dataUser)) {
            SessionCreatedListener.users.add(dataUser);
        }
        String id = req.getParameter("id_chat");
        ArrayList<String> arr = SessionCreatedListener.rooms;
        for (String key : arr) {
            if (Objects.equals(id, key) && id != null) {
                session.setAttribute("room", id);
                resp.sendRedirect("/room");
                return;
            }
        }
        req.getRequestDispatcher("/WEB-INF/not_found.jsp").forward(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/id_chat.jsp").forward(req, resp);

    }
}