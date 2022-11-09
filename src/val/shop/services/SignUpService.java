package val.shop.services;

import val.shop.dto.UserForm;

import java.sql.SQLException;

public interface SignUpService {
    void signUp(UserForm form) throws SQLException;
}
