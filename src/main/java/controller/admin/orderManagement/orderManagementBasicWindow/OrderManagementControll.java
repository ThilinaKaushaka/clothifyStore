package controller.admin.orderManagement.orderManagementBasicWindow;

import DBConnection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManagementControll {


    public String getNewOrderId() throws SQLException {
        Integer idCounter=getOrderID();
        ++idCounter;
        return "ORD"+idCounter;
    }

    private Integer getOrderID() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select idCounter from orderidcount");
        return resultSet.next()?resultSet.getInt(1):null;
    }

}
