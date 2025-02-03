package controller.admin.employeeManagement.employeManagementBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.employee.EmployeeBasicDetails;
import model.employee.EmployeeDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeManagementControll {


    public EmployeeDetails getEmployeeDetails(String id) throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select * from employee where id='" + id + "'");
        return resultSet.next()?new EmployeeDetails(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getDouble(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12)):null;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        return DBConnection.getDbConnection().getConnection().createStatement().executeUpdate("delete from employee where id='" + id + "'")>0;
    }

    public ObservableList<EmployeeBasicDetails> getEmployeeDetailsList() throws SQLException {
        ObservableList<EmployeeBasicDetails> basicList = FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select id,name,position,salary from employee;");
        while (resultSet.next())basicList.add(new EmployeeBasicDetails(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
        return basicList;
    }

    public Boolean updateDetails(EmployeeDetails employeeDetails) throws SQLException {System.out.println(employeeDetails);

        System.out.println(employeeDetails);
        String SQL="update employee set title=?, name=?, gender=?, depId=?, position=?, birthDay=?, address=?, salary=?, email=?, tpNo=?, assigningDate=? where id=?";
        PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement(SQL);
        preparedStatement.setObject(1, employeeDetails.getTitle());
        preparedStatement.setObject(2,employeeDetails.getName());
        preparedStatement.setObject(3, employeeDetails.getGender());
        preparedStatement.setObject(4, employeeDetails.getDpId());
        preparedStatement.setObject(5, employeeDetails.getPosition());
        preparedStatement.setObject(6, employeeDetails.getBirthDay());
        preparedStatement.setObject(7, employeeDetails.getAddress());
        preparedStatement.setObject(8,employeeDetails.getSalary());
        preparedStatement.setObject(9, employeeDetails.getEmail());
        preparedStatement.setObject(10, employeeDetails.getTpNo());
        preparedStatement.setObject(11, employeeDetails.getAssigningDate());
        preparedStatement.setObject(12, employeeDetails.getEmpId());

        return preparedStatement.executeUpdate()>0;
    }
}
