package model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class OrderMiniTableModel {
    private String id;
    private String cid;
    private String method;
    private String date;
    private Double total;
}
