package controller.admin.placeOrder.placeOrderBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.customer.CustomerBasicDetails;
import model.order.Order;
import model.order.OrderDetails;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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

    public Boolean placeOrder(Order order) throws SQLException {
        Connection connection=DBConnection.getDbConnection().getConnection();
        try {
            connection.setAutoCommit(false);
            if(addOrder(order)){
                if(addOrderDetails(order.getOrderDetailsList())){
                    if(updateItems(order.getOrderDetailsList())){
                        if(updateOrderIdCounter()){
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;

        }finally {
            connection.setAutoCommit(true);
        }



    }

    private boolean updateOrderIdCounter() throws SQLException {
        return DBConnection.getDbConnection().getConnection().createStatement().executeUpdate("update orderidcounter set Count=Count+1 where id=1")>0;
    }

    private boolean updateItems(List<OrderDetails> orderDetailsList) throws SQLException {
        for (OrderDetails orderDetail:orderDetailsList){
            PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("update item set qty=qty-? where id=?");
            preparedStatement.setInt(1,orderDetail.getQty());
            preparedStatement.setString(2,orderDetail.getItemId());

            if(preparedStatement.executeUpdate()<1)return false;
        }
        return true;
    }

    private boolean addOrderDetails(List<OrderDetails> orderDetailsList) throws SQLException {
        for (OrderDetails orderDetail:orderDetailsList){
            PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("insert into orderdetails values(?,?,?,?,?)");
            preparedStatement.setString(1,orderDetail.getOrderId());
            preparedStatement.setString(2,orderDetail.getItemId());
            preparedStatement.setInt(3,orderDetail.getQty());
            preparedStatement.setDouble(4,orderDetail.getUnitprice());
            preparedStatement.setDouble(5,orderDetail.getDiscount());

            if(preparedStatement.executeUpdate()<1)return false;
        }

        return true;
    }


    private boolean addOrder(Order order) throws SQLException {
        Connection connection=DBConnection.getDbConnection().getConnection();
        String SQL="INSERT INTO orders VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(SQL);
        pst.setString(1,order.getId());
        pst.setString(2,order.getLoyaltyCustomerId());
        pst.setDouble(3,order.getTotalPrice());
        pst.setString(4,order.getPaymentMethod());
        pst.setString(5,order.getDate());
        pst.setString(6,order.getTime());
        pst.setString(7,order.getEmpId());

        System.out.println(pst);

        return  pst.executeUpdate()>0;
    }



}
