package model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CustomerBasicDetails {
    String id;
    String name;
    String tpNo;
}
