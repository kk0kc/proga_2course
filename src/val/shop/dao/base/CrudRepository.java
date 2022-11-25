package val.shop.dao.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    T save(T item) throws SQLException;
}
