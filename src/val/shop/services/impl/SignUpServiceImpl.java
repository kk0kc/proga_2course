package val.shop.services.impl;

import val.shop.dao.UsersRepository;
import val.shop.dto.UserForm;
import val.shop.model.User;
import val.shop.services.SignUpService;

import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService {
    private final UsersRepository usersRepository;

    public SignUpServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void signUp(UserForm form) throws SQLException {
        User user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .status(form.getStatus())
                .build();
        usersRepository.save(user);
    }
}
