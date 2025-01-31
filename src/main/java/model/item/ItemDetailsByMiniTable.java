package model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItemDetailsByMiniTable {
    private String id;
    private String name;
    private String size;
    private Double price;
}
