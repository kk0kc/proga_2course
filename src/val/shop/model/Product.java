package val.shop.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Product {
	private int id;
	private String name;
	private String category;
	private Double price;
	private String image;

}
