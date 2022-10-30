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
    String SQLbook ="SELECT * FROM hw.book";
    String SQLshop ="SELECT * FROM hw.shop";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        PreparedStatement statement =null;
        createTables(connection,req);
        List<Shop> shops = new LinkedList<>();
        List<Book> books = new LinkedList<>();
        List<Link> links = new LinkedList<>();
        List<List<String>> mainTab = new LinkedList<>();
        List<Integer> delete=new LinkedList<>();
        String SQLRead;
        if(req.getParameter("columnBook")!=null && req.getParameter("filterBook")!=null && req.getParameter("columnBook")!="" && req.getParameter("filterBook")!=""){
            SQLRead = "SELECT * FROM hw.book WHERE "+req.getParameter("columnBook")+"= "+"'"+req.getParameter("filterBook")+"';";
        }else{
            SQLRead = SQLbook;
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
        if(req.getParameter("columnShop")!=null && req.getParameter("filterShop")!=null && req.getParameter("columnShop")!="" && req.getParameter("filterShop")!=""){
            SQLRead = "SELECT * FROM hw.shop WHERE "+req.getParameter("columnShop")+"= "+"'"+req.getParameter("filterShop")+"';";
        }else{
            SQLRead = SQLshop;
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
        for (Book b : books) {
            for (Shop s : shops) {
                for (Link l : links) {
                    if (b.getId() == l.getIdBook() && s.getId() == l.getIdShop()) {
                        List<String> q = new LinkedList<>();
                        q.add(String.valueOf(b.getId()));
                        q.add(b.getName());
                        q.add(b.getAuthor());
                        q.add(String.valueOf(s.getId()));
                        q.add(s.getName());
                        q.add(s.getAddress());
                        mainTab.add(q);
                    }
                }
            }
            req.setAttribute("mainTab", mainTab);
        }

        List<String> p = new LinkedList<>();
        req.setAttribute("newBook", req.getParameter("newBook"));
        req.setAttribute("newShop", req.getParameter("newShop"));
        req.setAttribute("changeBook", req.getParameter("changeBook"));
        req.setAttribute("changeShop", req.getParameter("changeShop"));
        req.setAttribute("CreateLink",req.getParameter("CreateLink"));
        req.setAttribute("DeleteLink",req.getParameter("DeleteLink"));

        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
            if (req.getAttribute("newBook") != null || req.getAttribute("changeBook") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "book", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("newShop") != null || req.getAttribute("changeShop") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "shop", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateLink") != null || req.getAttribute("DeleteLink") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "link", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            req.getSession().setAttribute("ColumnsOfTable",p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(req.getParameter("Delete")!=null){

            for(int i=0;i< books.size();i++){
                try {
                    String SQLDelete="DELETE FROM hw.book WHERE id=?";
                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("book"+books.get(i).getId()+"")!=null){
                        delete.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("book"+books.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer d: delete){
                books.remove(d);
            }
            delete=new LinkedList<>();
            for(int i=0;i< shops.size();i++){
                try {

                    String SQLDelete="DELETE FROM hw.shop WHERE id=?";

                    statement=connection.prepareStatement(SQLDelete);
                    if(req.getParameter("shop"+shops.get(i).getId()+"")!=null){
                        delete.add(i);
                        statement.setInt(1,Integer.parseInt(req.getParameter("shop"+shops.get(i).getId()+"")));
                        statement.execute();
                    }
                }catch (SQLException e){
                    throw new  RuntimeException(e);
                }
            }
            for(Integer d: delete){
                shops.remove(d);
            }
            resp.sendRedirect("/");
        }else {
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/main.jsp");
        requestDispatcher.forward(req,resp);}
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection connection = PostgresConnectionToDataBase.getConnection();
        createTables(connection,req);

        List<Shop> shops = new LinkedList<>();
        List<Book> books = new LinkedList<>();
        List<Link> links = new LinkedList<>();
        List<List<String>> mainTab = new LinkedList<>();
        List<Integer> delete=new LinkedList<>();
        String SQLRead;
        try {
            SQLRead = SQLbook;
            PreparedStatement statement = connection.prepareStatement(SQLRead);
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

        try {
            SQLRead = SQLshop;
            PreparedStatement statement = connection.prepareStatement(SQLRead);
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
            PreparedStatement statement = connection.prepareStatement(SQLRead);
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
        for (Book b : books) {
            for (Shop s : shops) {
                for (Link l : links) {
                    if (b.getId() == l.getIdBook() && s.getId() == l.getIdShop()) {
                        List<String> q = new LinkedList<>();
                        q.add(String.valueOf(b.getId()));
                        q.add(b.getName());
                        q.add(b.getAuthor());
                        q.add(String.valueOf(s.getId()));
                        q.add(s.getName());
                        q.add(s.getAddress());
                        mainTab.add(q);
                    }
                }
            }
            req.setAttribute("mainTab", mainTab);
        }







        List<String> p = new LinkedList<>();
        req.setAttribute("newBook", req.getParameter("newBook"));
        req.setAttribute("newShop", req.getParameter("newShop"));
        req.setAttribute("changeBook", req.getParameter("changeBook"));
        req.setAttribute("changeShop", req.getParameter("changeShop"));
        req.setAttribute("CreateLink",req.getParameter("CreateLink"));
        req.setAttribute("DeleteLink",req.getParameter("DeleteLink"));

        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
            if (req.getAttribute("newBook") != null || req.getAttribute("changeBook") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "book", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("newShop") != null || req.getAttribute("changeShop") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "shop", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (req.getAttribute("CreateLink") != null || req.getAttribute("DeleteLink") != null) {
                ResultSet resultSet = metaData.getColumns(null, "hw", "link", null);
                while (resultSet.next()) {
                    p.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            req.getSession().setAttribute("ColumnsOfTable",p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        List<String> columns= (List<String>) req.getSession().getAttribute("ColumnsOfTable");
        List<String> w=new LinkedList<>();

        if (req.getParameter("value1")!="" && req.getParameter("value1")!=null){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter(("value"+i+""))!=""){
                    w.add(req.getParameter("value"+i+""));
                }
            }
                String SQLCreateEmployee="INSERT INTO hw.book (name, author) VALUES (?,?)";
                try {
                    PreparedStatement statement=connection.prepareStatement(SQLCreateEmployee);
                    statement.setString(1,w.get(1));
                    statement.setString(2,w.get(2));
                    statement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

        }



        if(req.getParameter("value11")!="" && req.getParameter("value11")!=null){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter("value1"+i+"")!=""){
                    w.add(req.getParameter("value1"+i+""));
                }
            }
            String SQLCreateEmployee="INSERT INTO hw.shop (name, address) VALUES (?,?)";
            try {
                PreparedStatement statement=connection.prepareStatement(SQLCreateEmployee);
                statement.setString(1,w.get(1));
                statement.setString(2,w.get(2));
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(req.getParameter("valueForUpdate0")!=null && req.getParameter("valueForUpdate0")!=""){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter("valueForUpdate"+i+"")!=""){
                    w.add(req.getParameter("valueForUpdate"+i+""));
                }
            }
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


        if(req.getParameter("valueForUpdate11")!=null && req.getParameter("valueForUpdate11")!=""){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter("valueForUpdate1"+i+"")!=""){
                    w.add(req.getParameter("valueForUpdate1"+i+""));
                }
            }
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


        if(req.getParameter("valueForCreateLink1")!=null && req.getParameter("valueForCreateLink1")!=""){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter("valueForCreateLink"+i+"")!=""){
                    w.add(req.getParameter("valueForCreateLink"+i+""));
                }
            }
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

        if(req.getParameter("valueForDeleteLink1")!=null && req.getParameter("valueForDeleteLink1")!=""){
            for(int i=0;i< columns.size();i++){
                if(req.getParameter("valueForDeleteLink"+i+"")!=""){
                    w.add(req.getParameter("valueForDeleteLink"+i+""));
                }
            }
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
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("views/main.jsp");
        requestDispatcher.forward(req,resp);
    }





    public void createTables(Connection connection,HttpServletRequest req){
        DatabaseMetaData metaData = null;
        LinkedList<String> list=new LinkedList<>();
        try {
            metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "hw", "book", null);
            while (resultSet.next()) {
                list.add(resultSet.getString("COLUMN_NAME"));
            }
            LinkedList<String> r=list;
            req.setAttribute("ColumnsBookTable",r);
            list=new LinkedList<>();
            resultSet = metaData.getColumns(null, "hw", "shop", null);
            while (resultSet.next()) {
                list.add(resultSet.getString("COLUMN_NAME"));
            }
            req.setAttribute("ColumnsShopTable",list);
            req.setAttribute("ColumnsLinkTable", Stream.concat(r.stream(),list.stream()).toList());
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