package val.shop.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends Product{
	private int cartId;
	private int uid;
	private int quantity;
	private List<Order> products;
}
