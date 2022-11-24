package val.shop.dao;

import java.sql.*;
import java.util.*;

import val.shop.model.*;

public class OrderDao {
	
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    
    

	public OrderDao(Connection con) {
		super();
		this.con = con;
	}

    public void createOrderTab(){
        query = "create table if not exists orders" +
                "(" +
                "o_id bigserial NOT NULL," +
                "p_id int NOT NULL," +
                "u_id int NOT NULL," +
                "o_quantity int," +
                "o_date varchar(450) NOT NULL," +
                "PRIMARY KEY (o_id));";
        try {
            Statement statement = con.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void updateRate(int id, int rate) {
        //boolean result = false;
        try {
            query = "update orders set o_quantity = ? where o_id = ?;";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, rate);
            pst.setInt(2, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        //return result;
    }

    public List<Order> filterRate(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "select * from orders where u_id=? order by orders.o_quantity desc";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        return list;
    }

	public boolean insertOrder(Order model) {
        boolean result = false;
        try {
            query = "insert into orders (p_id, u_id, o_quantity, o_date) values(?,?,?,?)";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUid());
            pst.setInt(3, model.getQuantity());
            pst.setString(4, model.getDate());
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
	

    public List<Order> userOrders(int id) {
        List<Order> list = new ArrayList<>();
        try {
            query = "select * from orders where u_id=? order by orders.o_id desc";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                ProductDao productDao = new ProductDao(this.con);
                int pId = rs.getInt("p_id");
                Product product = productDao.getSingleProduct(pId);
                order.setOrderId(rs.getInt("o_id"));
                order.setId(pId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setImdb(product.getImdb()*rs.getInt("o_quantity"));
                order.setQuantity(rs.getInt("o_quantity"));
                order.setDate(rs.getString("o_date"));
                order.setImage(product.getImage());
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void cancelOrder(int id) {
        //boolean result = false;
        try {
            query = "delete from orders where o_id=?";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        //return result;
    }
}
