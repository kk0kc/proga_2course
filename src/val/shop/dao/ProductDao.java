package val.shop.dao;

import java.sql.*;
import java.util.*;

import val.shop.model.Cart;
import val.shop.model.Product;

public class ProductDao {
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    

	public ProductDao(Connection con) {
		super();
		this.con = con;
	}
	
	
	public List<Product> getAllProducts() {
        List<Product> book = new ArrayList<>();
        try {

            query = "select * from products";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
            	Product row = new Product();
                row.setId(rs.getInt("id"));
                row.setName(rs.getString("name"));
                row.setCategory(rs.getString("category"));
                row.setYear(rs.getInt("year"));
                row.setImdb(rs.getDouble("imdb"));
                row.setImage(rs.getString("image"));
                row.setGif(rs.getString("gif"));
                book.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return book;
    }
	
	
	 public Product getSingleProduct(int id) {
		 Product row = null;
	        try {
	            query = "select * from products where id=? ";

	            pst = this.con.prepareStatement(query);
	            pst.setInt(1, id);
	            ResultSet rs = pst.executeQuery();

	            while (rs.next()) {
	            	row = new Product();
	                row.setId(rs.getInt("id"));
	                row.setName(rs.getString("name"));
	                row.setCategory(rs.getString("category"));
                    row.setYear(rs.getInt("year"));
                    row.setImdb(rs.getDouble("imdb"));
	                row.setImage(rs.getString("image"));
                    row.setGif(rs.getString("gif"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }

	        return row;
	    }
	
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
        double sum = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select price from products where id=?";
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        sum+=rs.getDouble("price")*item.getQuantity();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }

    
    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> book = new ArrayList<>();
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select * from products where id=?";
                    pst = this.con.prepareStatement(query);
                    pst.setInt(1, item.getId());
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        Cart row = new Cart();
                        row.setId(rs.getInt("id"));
                        row.setName(rs.getString("name"));
                        row.setCategory(rs.getString("category"));
                        row.setYear(rs.getInt("year"));
                        row.setImdb(rs.getDouble("imdb"));
                        row.setQuantity(item.getQuantity());
                        row.setImage(rs.getString("image"));
                        row.setGif(rs.getString("gif"));
                        book.add(row);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return book;
    }

    public boolean insertNewProduct(Product model) {
        boolean result = false;
        try {
            query = "insert into products (name, category, year, imdb, image, gif) values(?,?,?,?,?,?)";
            pst = this.con.prepareStatement(query);
            pst.setString(1, model.getName());
            pst.setString(2, model.getCategory());
            pst.setInt(3, model.getYear());
            pst.setDouble(4, model.getImdb());
            pst.setString(5, model.getImage());
            pst.setString(6, model.getGif());
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void removeProduct(int id) {
        //boolean result = false;
        try {
            query = "delete from products where id=?";
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
