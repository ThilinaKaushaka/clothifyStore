package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemDetails {
    private String itemId;
    private String itemName;
    private String categoryName;
    private String brand;
    private String size;
    private Integer qty;
    private Double price;
    private Double discount;
}
