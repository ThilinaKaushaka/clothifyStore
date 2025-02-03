package model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EmployeeBasicDetails {
    private String id;
    private String name;
    private String position;
    private Double salary;
}
