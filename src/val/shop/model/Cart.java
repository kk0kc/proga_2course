package val.shop.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends Product{
	private int cartId;
	private int uid;
	private int quantity;
}
