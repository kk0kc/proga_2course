package val.shop.dao;

import java.sql.*;
import val.shop.model.*;

public class UserDao {
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;

	public UserDao(Connection con) {
		this.con = con;
	}

    public void createUsersTab(){
        query = "create table if not exists users" +
                "(" +
                "id bigserial NOT NULL," +
                "name varchar(250) NOT NULL," +
                "email varchar(250) NOT NULL," +
                "password varchar(250) NOT NULL," +
                "status varchar(50) NOT NULL," +
                "PRIMARY KEY (id)," +
                "UNIQUE (email));";
        try {
            Statement statement = con.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User userLogin(String email, String password, String status) {
        User user = null;
        try {
            query = "select * from users where email=? and password=? and status=?";
            pst = this.con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, status);
            rs = pst.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return user;
    }
}
