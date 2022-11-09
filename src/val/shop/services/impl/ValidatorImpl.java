package val.shop.services.impl;

import val.shop.dao.UsersRepository;
import val.shop.dto.UserForm;
import val.shop.services.validation.ErrorEntity;
import val.shop.services.validation.Validator;

import java.sql.SQLException;
import java.util.Optional;

public class ValidatorImpl implements Validator {
    private final UsersRepository usersRepository;

    public ValidatorImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<ErrorEntity> validateRegistration(UserForm form) throws SQLException {
        if(form.getEmail() == null) {
            return Optional.of(ErrorEntity.INVALID_EMAIL);
        } else if(usersRepository.findByEmail(form.getEmail()).isPresent()) {
            return Optional.of(ErrorEntity.EMAIL_ALREADY_TAKEN);
        }
        return Optional.empty();
    }
}
