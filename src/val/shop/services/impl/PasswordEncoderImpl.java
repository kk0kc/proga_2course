package val.shop.services.impl;

import val.shop.services.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }
}
