package model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class OrderDetails {
    private String orderId;
    private String itemId;
    private int qty;
    private Double unitprice;
    private Double discount;
}
