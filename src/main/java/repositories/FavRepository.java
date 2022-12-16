package repositories;

import config.PostgresConnectionProvider;
import models.Fav;
import models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FavRepository implements EntityRepository<Fav> {
    private static final String SELECT_ALL = "select * from fav_table";
//    private static final String UPDATE = "update fav_table set user_id = ?, post_id = ?";
    private static final String INSERT = "insert into fav_table (user_id, post_id) values (?, ?)";
    private static final Function<ResultSet, Fav> POST_MAPPER = resultSet -> {
        try {
            return Fav.builder()
                    .id(resultSet.getLong("id"))
                    .userID(resultSet.getLong("user_id"))
                    .postID(resultSet.getLong("post_id"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    @Override
    public List<Fav> findAll() {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Fav> favs = new ArrayList<>();

            while (resultSet.next()) {
                favs.add(POST_MAPPER.apply(resultSet));
            }

            return favs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Fav> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Fav fav) {
        try (Connection connection = PostgresConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, fav.getUserID());
            statement.setLong(2, fav.getPostID());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                fav.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Fav fav) {
//        try (Connection connection = PostgresConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
//            statement.setLong(1, fav.getUserID());
//            statement.setLong(2, fav.getPostID());
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void delete(Fav fav) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
