package val.shop.dao.impl;

//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import val.shop.DataBaseConnection.PostgresConnectionToDataBase;
import val.shop.dao.UsersRepository;
import val.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private final static String SQL_INSERT = "insert into users(name, email,password) " +
            "values (?, ?, ?)";
    private final static String SQL_UPDATE = "update sem.users set first_name = ?, last_name = ?, age = ?, password_hash = ?, email = ?, avatar_id = ? where id = ?";
    private final static String SQL_UPDATE_AVATAR = "update sem.users set avatar_id = ? where id = ?";
    private final static String SQL_UPDATE_TOKEN = "update sem.user_token set token = ? where user_id = ?";
    private final static String SQL_CREATE_TOKEN = "insert into sem.user_token(user_id, token) VALUES (?, ?)";
    private final static String SQL_SELECT_BY_ID = "select * from sem.users where id = ?";
    private final static String SQL_SELECT_TOKEN_BY_USER_ID = "select * from sem.user_token where user_id = ?";
    private final static String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
    private final static String SQL_SELECT_BY_TOKEN = "select * from sem.user_token join sem.users  on sem.user_token.user_id = sem.users.id where token = ?";
    private final static String SQL_SELECT_ALL = "select * from sem.users";

//    private final RowMapper<User> rowMapper = (row, rowNumber) ->
//            User.builder()
//                    .id(row.getLong("id"))
//                    .firstName(row.getString("first_name"))
//                    .lastName(row.getString("last_name"))
//                    .age(row.getInt("age"))
//                    .hashPassword(row.getString("password_hash"))
//                    .email(row.getString("email"))
//                    .avatarId(row.getLong("avatar_id") == 0 ? null : row.getLong("avatar_id"))
//                    .build();
//
//    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(PostgresConnectionToDataBase connection) {
        connection.getConnection();;
    }
//
    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        Connection connection = PostgresConnectionToDataBase.getConnection();
        PreparedStatement statement=connection.prepareStatement(SQL_SELECT_BY_EMAIL);
        statement.setString(1, email);
        if (statement == null){
            return Optional.of(new User());
        }
        return Optional.empty();

//        try {
//            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, rowMapper, email));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
    }
//
//    @Override
//    public Optional<User> findByToken(String token) {
//        try {
//            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, rowMapper, token));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }

//    @Override
//    public void updateAvatarForUser(Long userId, Long fileId) {
//        jdbcTemplate.update(SQL_UPDATE_AVATAR, fileId, userId);
//    }
//
//    @Override
//    public Optional<String> getTokenByUserId(Long userId) {
//        return jdbcTemplate.query(SQL_SELECT_TOKEN_BY_USER_ID, resultSet -> {
//           if (resultSet.next()) {
//               return Optional.of(resultSet.getString("token"));
//           } else {
//               return Optional.empty();
//           }
//        }, userId);
//    }
//
//    @Override
//    public void createTokenForUser(Long userId, String token) {
//        jdbcTemplate.update(SQL_CREATE_TOKEN, userId, token);
//    }
//
//    @Override
//    public void updateTokenForUser(Long userId, String token) {
//        jdbcTemplate.update(SQL_UPDATE_TOKEN, token, userId);
//    }
//
//    @Override
//    public Optional<User> findById(Long id) {
//        try {
//            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }

//    @Override
//    public List<User> findAll() {
//        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
//    }

    @Override
    public User save(User item) throws SQLException {
        if(item.getId() == null) {
            Connection connection = PostgresConnectionToDataBase.getConnection();
            PreparedStatement statement=connection.prepareStatement(SQL_INSERT);
            statement.setString(1, item.getName());
            statement.setString(3, item.getPassword());
            statement.setString(2, item.getEmail());
            statement.execute();

//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            jdbcTemplate.update(connection -> {
//                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
//                statement.setString(1, item.getFirstName());
//                statement.setString(2, item.getLastName());
//                statement.setInt(3, item.getAge());
//                statement.setString(4, item.getHashPassword());
//                statement.setString(5, item.getEmail());
//                return statement;
//            }, keyHolder);
//            if (keyHolder.getKey() != null) {
//                item.setId(keyHolder.getKey().longValue());
//            }
//        } else {
//            jdbcTemplate.update(SQL_UPDATE,
//                    item.getFirstName(),
//                    item.getLastName(),
//                    item.getAge(),
//                    item.getHashPassword(),
//                    item.getEmail(),
//                    item.getAvatarId(),
//                    item.getId()
//            );
        }
        return item;
    }


    // TODO: Implement
    @Override
    public void delete(Long id) {}
}
