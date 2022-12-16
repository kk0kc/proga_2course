package temp.repositories;

import config.PostgresConnectionProvider;
import models.Fav;
import models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FavRepository {
    public List<Fav> findAll() {
        // language=SQL
        String findAll = "select * from fav_table";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAll);
            ResultSet resultSet = statement.executeQuery();

            List<Fav> list = new LinkedList<>();

            while (resultSet.next()) {
                Fav fav = Fav.builder()
                        .id(resultSet.getLong("id"))
                        .userID(resultSet.getLong("user_id"))
                        .postID(resultSet.getLong("post_id"))
                        .build();

                list.add(fav);
            }

            return list;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
    public void save(Fav fav) {
        // language=SQL
        String save = "insert into fav_table (user_id, post_id) values (?, ?)";
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(save);
            statement.setLong(1, fav.getUserID());
            statement.setLong(2, fav.getPostID());

            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
//    public void update(Fav fav) {
//        // language=SQL
//        String update = "update fav_table set user_id = ?, post_id = ?";
//        try (Connection connection = PostgresConnectionProvider.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(update);
//            statement.setLong(1, fav.getUserID());
//            statement.setLong(2, fav.getPostID());
//
//            statement.execute();
//        } catch (SQLException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
}
