package val.shop.dao;

import val.shop.model.Cart;
import val.shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;



	public CartDao(Connection con) {  //-- ?
		super();
		this.con = con;
	}

	public boolean insertCart(Cart model) {
        boolean result = false;
        try {
            query = "insert into cart (p_id, u_id, o_quantity) values(?,?,?)";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, model.getId());
            pst.setInt(2, model.getUid());
            pst.setInt(3, model.getQuantity());
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
	

    public List<Cart> userCart(int id) {
        List<Cart> list = new ArrayList<>();
        try {
            query = "select * from cart where u_id=? order by cart.c_id desc";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                ProductDao productDao = new ProductDao(this.con);
                int pId = rs.getInt("p_id");
                Product product = productDao.getSingleProduct(pId);
                cart.setCartId(rs.getInt("c_id"));
                cart.setId(pId);
                cart.setImage(product.getImage());
                cart.setName(product.getName());
                cart.setCategory(product.getCategory());
                cart.setImdb(product.getImdb()*rs.getInt("o_quantity"));
                cart.setQuantity(rs.getInt("o_quantity"));
                list.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Cart> selectAllCart() {
        List<Cart> list = new ArrayList<>();
        try {
            query = "select * from cart order by cart.c_id desc";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                ProductDao productDao = new ProductDao(this.con);
                int pId = rs.getInt("p_id");
                Product product = productDao.getSingleProduct(pId);
                cart.setCartId(rs.getInt("c_id"));
                cart.setId(pId);
                cart.setName(product.getName());
                cart.setCategory(product.getCategory());
                cart.setImdb(product.getImdb()*rs.getInt("o_quantity"));
                cart.setImage(product.getImage());
                cart.setQuantity(rs.getInt("o_quantity"));
                list.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void updateQuantityOfCart(int id, int quantity) {
        //boolean result = false;
        try {
            query = "update cart set o_quantity = ? where p_id = ?;";
            pst = this.con.prepareStatement(query);
            pst.setInt(1, quantity);
            pst.setInt(2, id);
            pst.execute();
            //result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        //return result;
    }

    public void cancelCart(int id) {
        //boolean result = false;
        try {
            query = "delete from cart where p_id=?";
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
