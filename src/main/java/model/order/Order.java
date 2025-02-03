package model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Order {
    private String id;
    private String loyaltyCustomerId;
    private Double totalPrice;
    private String paymentMethod;
    private String date;
    private String time;
    private String empId;
    private List<OrderDetails> orderDetailsList;


}
