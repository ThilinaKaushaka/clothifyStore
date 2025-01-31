package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemDetailsByItemCode {

    private String size;
    private Integer available;
    private String brand;
    private Double discount;
    private Double price;
}
