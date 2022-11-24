package val.shop.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Order extends Product{
	private int orderId;
	private int uid;
	private int quantity;
	private String date;

}
