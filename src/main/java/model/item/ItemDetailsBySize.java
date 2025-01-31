package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString

public class ItemDetailsBySize {
    private Integer available;
    private String brand;
    private Double discount;
    private Double unidPrice;
}
