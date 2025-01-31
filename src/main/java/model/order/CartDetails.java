package model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CartDetails {
    private String itemCode;
    private String name;
    private String size;
    private String brand;
    private Integer qty;
    private Double discount;
    private Double unitPrice;
    private Double subTotal;

    public boolean equals(Object obj) {
        CartDetails cartDetails=(CartDetails) obj;
        return this.itemCode.equals(cartDetails.itemCode);
    }
}
