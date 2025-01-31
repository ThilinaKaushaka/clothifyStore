package controller.admin.placeOrder.placeOrderBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customer.CustomerBasicDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceOrderBasicControll {

    public CustomerBasicDetails searchCustomerOrderWindow(String customerId) throws SQLException {
        CustomerBasicDetails details = getCustomerBasicDetails(customerId);
        return details!=null?details:null;

    }

    private CustomerBasicDetails getCustomerBasicDetails(String customerId) throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select `name`,tpNo from loyaltyCustomer where id='" + customerId + "'");
        return resultSet.next()?new CustomerBasicDetails(customerId,resultSet.getString(1),resultSet.getString(2)):null;
    }

    public ObservableList getPaymentMethods() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select * from paymentMethod");
        ObservableList<String> paymentMethod = FXCollections.observableArrayList();
        while (resultSet.next())paymentMethod.add(resultSet.getString(1));
        return paymentMethod;
    }
}
