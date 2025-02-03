package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ItemMiniTableModel {
    private String id;
    private String name;
    private String category;
    private Integer qty;
    private Double discount;
    private Double price;
}
