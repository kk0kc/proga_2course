package val.shop.dao;

import val.shop.dao.base.CrudRepository;
import val.shop.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
//    Optional<User> findByEmail(String email) throws SQLException;
}
