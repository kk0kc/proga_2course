package val.shop.dao.impl;

import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.UsersRepository;
import val.shop.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private final static String SQL_INSERT = "insert into users(name, email,password, status) " +
            "values (?, ?, ?, ?)";
//    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";


    public UsersRepositoryImpl(PostgresConnectionToDataBase connection) {
        connection.getConnection();;
    }
//
//    @Override
//    public Optional<User> findByEmail(String email) throws SQLException {
//        Connection connection = PostgresConnectionToDataBase.getConnection();
//        PreparedStatement statement=connection.prepareStatement(SQL_SELECT_BY_EMAIL);
//        statement.setString(1, email);
//        if (statement == null){
//            return Optional.of(new User());
//        }
//        return Optional.empty();
//    }


    @Override
    public User save(User item) throws SQLException {
        if(item.getId() == null) {
            Connection connection = PostgresConnectionToDataBase.getConnection();
            PreparedStatement statement=connection.prepareStatement(SQL_INSERT);
            statement.setString(1, item.getName());
            statement.setString(3, item.getPassword());
            statement.setString(2, item.getEmail());
            statement.setString(4, item.getStatus());
            statement.execute();
        }
        return item;
    }
}
