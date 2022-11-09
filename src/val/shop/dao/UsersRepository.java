package val.shop.dao;

import val.shop.dao.base.CrudRepository;
import val.shop.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email) throws SQLException;
//    Optional<User> findByToken(String token);
//    void updateAvatarForUser(Long userId, Long fileId);
//    Optional<String> getTokenByUserId(Long userId);
//    void createTokenForUser(Long userId, String token);
//    void updateTokenForUser(Long userId, String token);
}
