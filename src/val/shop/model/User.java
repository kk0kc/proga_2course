package val.shop.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String status;
}

