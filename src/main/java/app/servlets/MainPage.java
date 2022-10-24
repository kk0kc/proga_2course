package app.servlets;

import app.DataBaseConnection.PostgresConnectionToDataBase;
import app.entities.Book;
import app.entities.Link;
import app.entities.Shop;
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
import java.util.stream.Stream;

@WebServlet("")
public class MainPage extends HttpServlet {
    String PastSelectForEmployee="SELECT * FROM hw.book";
    String PastSelectForPosition="SELECT * FROM hw.shop";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        PreparedStatement statement =null;
        createTables(connection,req);

        List<Shop> shops = new LinkedList<>();
        List<Book> books = new LinkedList<>();
        List<Link> links = new LinkedList<>();
        List<List<String>> outList = new LinkedList<>();
        List<Integer> deleteThisFromList=new LinkedList<>();
        String SQLRead;






        if(req.getParameter("column")!=null && req.getParameter("ascendingOrDescending")!=null && req.getParameter("column")!="" && req.getParameter("ascendingOrDescending")!=""){
            SQLRead = "SELECT * FROM hw.book order by "+req.getParameter("column")+" "+req.getParameter("ascendingOrDescending")+"";
            PastSelectForEmployee=SQLRead;
        }else{
            SQLRead = PastSelectForEmployee;
        }
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = Book.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .author(resultSet.getString(3))
                        .build();
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        if(req.getParameter("column1")!=null && req.getParameter("ascendingOrDescending1")!=null && req.getParameter("column1")!="" && req.getParameter("ascendingOrDescending1")!=""){
            SQLRead = "SELECT * FROM hw.shop order by "+req.getParameter("column1")+" "+req.getParameter("ascendingOrDescending1");
            PastSelectForPosition=SQLRead;
        }else{
            SQLRead = PastSelectForPosition;
        }
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Shop shop = Shop.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .address(resultSet.getString(3))
                        .build();
                shops.add(shop);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        SQLRead = "SELECT * FROM hw.link";
        try {
            statement = connection.prepareStatement(SQLRead);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Link link = Link.builder()
                        .idBook(resultSet.getInt(2))
                        .idShop(resultSet.getInt(3))
                        .build();
                links.add(link);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        req.setAttribute("books", books);
        req.setAttribute("shops", shops);

        boolean booksWithoutShop = true;
        for (Book e : books) {
            for (Shop p : shops) {
                for (Link l : links) {
                    if (e.getId() == l.getIdBook() && p.getId() == l.getIdShop()) {
                        booksWithoutShop = false;
                        List<String> q = new LinkedList<>();
                        q.add(String.valueOf(e.getId()));
                        q.add(e.getName());
                        q.add(e.getAuthor());
                        q.add(String.valueOf(p.getId()));
                        q.add(p.getName());
                        q.add(p.getAddress());
                        outList.add(q);
                    }
                }
            }
            if (booksWithoutShop) {
                List<String> q = new LinkedList<>();
                q.add(String.valueOf(e.getId()));
                q.add(e.getName());
                q.add(e.getAuthor());
                outList.add(q);
            }
            booksWithoutShop = true;
            req.setAttribute("outList", outList);
        }


        if(req.getParameter("Delete")!=null){

            for(int i=0;i< books.size();i++){
                try {
                    String SQLDelete="DELETE FROM hw.book WHERE id=?";
                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("book"+books.get(i).getId()+"")!=null){
                        deleteThisFromList.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("book"+books.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer i: deleteThisFromList){
                    books.remove(i);
            }
            deleteThisFromList=new LinkedList<>();
            for(int i=0;i< shops.size();i++){
                try {

                    String SQLDelete="DELETE FROM hw.shop WHERE id=?";

                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("shop"+shops.get(i).getId()+"")!=null){
                        deleteThisFromList.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("shop"+shops.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer i: deleteThisFromList){
                shops.remove(i);
            }
            resp.sendRedirect("/");
        }else {
            RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/index.jsp");
            requestDispatcher.forward(req,resp);
        }


        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/index.jsp");
        requestDispatcher.forward(req,resp);


    }





    public void createTables(Connection connection,HttpServletRequest req){
        DatabaseMetaData md = null;
        LinkedList<String> p=new LinkedList<>();
        try {
            md = connection.getMetaData();
            ResultSet resultSet = md.getColumns(null, "hw", "book", null);
            while (resultSet.next()) {
                p.add(resultSet.getString("COLUMN_NAME"));
            }
            LinkedList<String> r=p;
            req.setAttribute("ColumnsBookTable",r);
            p=new LinkedList<>();
            resultSet = md.getColumns(null, "hw", "shop", null);
            while (resultSet.next()) {
                p.add(resultSet.getString("COLUMN_NAME"));
            }
            req.setAttribute("ColumnsShopTable",p);
            req.setAttribute("ColumnsLinkTable", Stream.concat(r.stream(),p.stream()).toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String sqlBookTable = "create table if not exists hw.book" +
                "(" +
                "id bigserial primary key," +
                "name varchar(30)," +
                "author varchar(30));";

        String sqlShopTable = "create table if not exists hw.shop" +
                "(" +
                "id bigserial primary key," +
                "name varchar(30)," +
                "address varchar(50)" +
                ");";
        String sqlLinkTable = "create table if not exists hw.link" +
                "(" +
                "id bigserial primary key," +
                "id_book int," +
                "id_shop int" +
                ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlBookTable);
            statement.execute(sqlShopTable);
            statement.execute(sqlLinkTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}