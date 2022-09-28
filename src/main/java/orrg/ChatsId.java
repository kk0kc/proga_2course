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

import static java.util.Objects.nonNull;

@WebServlet(name = "MainServlet", urlPatterns = "/id")
public class ChatsId extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id_chat");
        ServletContext servletContext = req.getServletContext();
        ArrayList<String> arr = SessionCreatedListener.rooms;


        for (String key : arr) {
            if (Objects.equals(id, key)) {
                servletContext.setAttribute("room", key);
                resp.sendRedirect("/room");
                return;
            } else {
                req.getRequestDispatcher("/WEB-INF/not_found.jsp").forward(req, resp);
                return;
            }
        }

        req.getRequestDispatcher("/WEB-INF/id_chat.jsp").forward(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String dataId = req.getParameter("id");
        ArrayList<String> arr = SessionCreatedListener.rooms;

        for (String key : arr) {
            if (Objects.equals(key, dataId)) {
                req.getRequestDispatcher("/WEB-INF/already_have.jsp").forward(req, resp);
                return;
            }
        }

        SessionCreatedListener.rooms.add(dataId);
        req.getRequestDispatcher("/WEB-INF/id_chat.jsp").forward(req, resp);





//        String id = req.getParameter("id_chat");
//        ServletContext servletContext = req.getServletContext();
//
//
//        for (String key : arr) {
//            if (Objects.equals(id, key)) {
//                servletContext.setAttribute("room", key);
//                resp.sendRedirect("/note");
//                return;
//            }
//        }
//
//        req.getRequestDispatcher("/WEB-INF/jsp/not-reg.jsp").forward(req, resp);
    }



}