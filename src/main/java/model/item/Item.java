package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Item {
    private String itemCode;
    private String name;
    private String categoryId;
    private String categoryName;
    private String brand;
    private String colour;
    private String size;
    private Double discount;
    private Double price;
    private Integer qty;
    private String supplierId;
    private String wareHouseId;
}
