package app.servlets;

import app.DataBaseConnection.PostgresConnectionToDataBase;
import app.entities.ColumnAndType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/change")
public class ChangeTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        List<String> p = new LinkedList<>();
        req.setAttribute("CreateNewPosition", req.getParameter("CreateNewPosition"));
        req.setAttribute("CreateNewEmployee", req.getParameter("CreateNewEmployee"));
        req.setAttribute("UpdateEmployee", req.getParameter("UpdateEmployee"));
        req.setAttribute("UpdatePosition", req.getParameter("UpdatePosition"));
        req.setAttribute("CreateLink",req.getParameter("CreateLink"));
        req.setAttribute("DeleteLink",req.getParameter("DeleteLink"));

        DatabaseMetaData md = null;
        try {
            md = connection.getMetaData();
            if (req.getAttribute("CreateNewEmployee") != null || req.getAttribute("UpdateEmployee") != null) {
                ResultSet resultSet = md.getColumns(null, null, "book", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateNewPosition") != null || req.getAttribute("UpdatePosition") != null) {
                ResultSet resultSet = md.getColumns(null, "hw", "shop", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateLink") != null || req.getAttribute("DeleteLink") != null) {
                ResultSet resultSet = md.getColumns(null, "hw", "link", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            req.getSession().setAttribute("ColumnsOfTable",p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/CreateOrChange.jsp");
        requestDispatcher.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ColumnAndType> r= (List<ColumnAndType>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();
        Connection connection=PostgresConnectionToDataBase.getConnection();
        boolean allIsInsert=true;
        if (req.getParameter("value1")!="" && req.getParameter("value1")!=null){
            for(int i=0;i< r.size();i++){
                if(req.getParameter(("value"+i+""))!=""){
                    w.add(req.getParameter("value"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLCreateEmployee="INSERT INTO hw.book (name, author) VALUES (?,?)";
                try {
                    PreparedStatement statement=connection.prepareStatement(SQLCreateEmployee);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(req.getParameter("idOfPosition")!=""){
                    String s=req.getParameter("idOfPosition");
                    String[] o=s.split(",");
                    String SQLFindCurrentID="SELECT * FROM hw.book WHERE ID=(SELECT MAX(ID) FROM hw.book)";
                    String SQLCreateLink="INSERT INTO hw.link (id_book,id_shop) VALUES (?,?)";
                    for(int i=0;i<o.length;i++){
                        try {
                            PreparedStatement statement=connection.prepareStatement(SQLCreateLink);
                            PreparedStatement statement1=connection.prepareStatement(SQLFindCurrentID);
                            ResultSet resultSet=statement1.executeQuery();
                            resultSet.next();
                            statement.setInt(1,Integer.parseInt(resultSet.getString("id")));
                            statement.setInt(2,Integer.parseInt(o[i]));
                            statement.execute();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            }



        if(req.getParameter("value11")!="" && req.getParameter("value11")!=null){
            for(int i=0;i< r.size();i++){
                if(req.getParameter("value1"+i+"")!=""){
                    w.add(req.getParameter("value1"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLCreateEmployee="INSERT INTO hw.shop (name, address) VALUES (?,?)";
                try {
                    PreparedStatement statement=connection.prepareStatement(SQLCreateEmployee);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(req.getParameter("idOfEmployee")!=""){
                    String s=req.getParameter("idOfEmployee");
                    String[] o=s.split(",");
                    String SQLFindCurrentID="SELECT * FROM hw.shop WHERE ID=(SELECT MAX(ID) FROM hw.shop)";
                    String SQLCreateLink="INSERT INTO hw.link (id_book,id_shop) VALUES (?,?)";
                    for(int i=0;i<o.length;i++){
                        try {
                            PreparedStatement statement=connection.prepareStatement(SQLCreateLink);
                            PreparedStatement statement1=connection.prepareStatement(SQLFindCurrentID);
                            ResultSet resultSet=statement1.executeQuery();
                            resultSet.next();
                            statement.setInt(1,Integer.parseInt(o[i]));
                            statement.setInt(2,Integer.parseInt(resultSet.getString("id")));
                            statement.execute();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }

        if(req.getParameter("valueForUpdate0")!=null && req.getParameter("valueForUpdate0")!=""){
            for(int i=0;i< r.size();i++){
                if(req.getParameter("valueForUpdate"+i+"")!=""){
                    w.add(req.getParameter("valueForUpdate"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLUpdate="UPDATE hw.book SET name=?,author=? WHERE id=?";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.setInt(3,Integer.parseInt(w.get(0)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }

        }

        if(req.getParameter("valueForUpdate11")!=null && req.getParameter("valueForUpdate11")!=""){
            for(int i=0;i< r.size();i++){
                if(req.getParameter("valueForUpdate1"+i+"")!=""){
                    w.add(req.getParameter("valueForUpdate1"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLUpdate="UPDATE hw.shop SET name=?, address=? WHERE id=?";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.setInt(3,Integer.parseInt(w.get(0)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }

        }

        if(req.getParameter("valueForCreateLink1")!=null && req.getParameter("valueForCreateLink1")!=""){
            for(int i=0;i< r.size();i++){
                if(req.getParameter("valueForCreateLink"+i+"")!=""){
                    w.add(req.getParameter("valueForCreateLink"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLUpdate="INSERT INTO hw.link (id_book,id_shop) VALUES (?,?)";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setInt(1,Integer.parseInt(w.get(1)));
                    statement.setInt(2,Integer.parseInt(w.get(2)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }

        }

        if(req.getParameter("valueForDeleteLink1")!=null && req.getParameter("valueForDeleteLink1")!=""){
            for(int i=0;i< r.size();i++){
                if(req.getParameter("valueForDeleteLink"+i+"")!=""){
                    w.add(req.getParameter("valueForDeleteLink"+i+""));
                }else{
                    allIsInsert=false;
                    req.setAttribute("Error",allIsInsert);
                }
            }
            if(allIsInsert){
                String SQLUpdate="DELETE FROM hw.link WHERE id_book=? and id_shop=?";
                try{
                    PreparedStatement statement=connection.prepareStatement(SQLUpdate);
                    statement.setInt(1,Integer.parseInt(w.get(1)));
                    statement.setInt(2,Integer.parseInt(w.get(2)));
                    statement.execute();
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }

        }


        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/CreateOrChange.jsp");
        requestDispatcher.forward(req,resp);
    }
}