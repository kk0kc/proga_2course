package val.shop.services.impl;

import val.shop.dao.UsersRepository;
import val.shop.dto.UserForm;
import val.shop.exceptions.ValidationException;
import val.shop.model.User;
import val.shop.services.PasswordEncoder;
import val.shop.services.SignUpService;
import val.shop.services.validation.ErrorEntity;
import val.shop.services.validation.Validator;

import java.sql.SQLException;
import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public SignUpServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public void signUp(UserForm form) throws SQLException {
        Optional<ErrorEntity> optionalError = validator.validateRegistration(form);
        if(optionalError.isPresent()) {
            throw new ValidationException(optionalError.get());
        }
        User user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(passwordEncoder.encode(form.getPassword()))
                .status(form.getStatus())
                .build();
        usersRepository.save(user);
    }
}
