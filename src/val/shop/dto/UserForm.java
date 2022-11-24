package val.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserForm {
    private String email;
    private String name;
    private String password;
    private String status;
}
