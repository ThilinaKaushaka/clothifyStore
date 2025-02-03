package model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EmployeeDetails {
    private String empId;
    private String title;
    private String name;
    private String gender;
    private String dpId;
    private String position;
    private String birthDay;
    private String address;
    private Double salary;
    private String email;
    private String tpNo;
    private String assigningDate;
}
