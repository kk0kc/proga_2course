package val.shop.services.validation;

import val.shop.dto.UserForm;

import java.sql.SQLException;
import java.util.Optional;

public interface Validator {
    Optional<ErrorEntity> validateRegistration(UserForm form) throws SQLException;
}
