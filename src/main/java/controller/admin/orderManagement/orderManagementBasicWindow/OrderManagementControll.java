package controller.admin.orderManagement.orderManagementBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.order.Order;
import model.order.OrderDetails;
import model.order.OrderMiniTableModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementControll {


    public String getNewOrderId() throws SQLException {
        Integer idCounter=getOrderID();
        ++idCounter;
        return "ORD"+idCounter;
    }

    private Integer getOrderID() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select `Count` from orderidcounter where id=1");
        return resultSet.next()?resultSet.getInt(1):null;
    }

    public Order getOrderDetails(String id) throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select * from `orders` where id='" + id + "'");


        if(resultSet.next()){

            return new Order(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),getOrderDetailsList(resultSet.getString(1)));


        }
        return null;

    }

    private List<OrderDetails> getOrderDetailsList(String id) throws SQLException {
        List<OrderDetails>list=new ArrayList<>();
        ResultSet resultSet=DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select * from orderdetails where orderId='" + id + "'");
        while (resultSet.next()){
            list.add(new OrderDetails(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4),resultSet.getDouble(5)));
        }

        return list;
    }

    public ObservableList<OrderMiniTableModel> getMiniDableModel() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select id,loyaltyCustomerId,paymentMethod,date,totalPrice from orders");
        ObservableList<OrderMiniTableModel> list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new OrderMiniTableModel(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDouble(5)));
        }

        return list;
    }

    public boolean deleteOrder(String id) throws SQLException {
        return DBConnection.getDbConnection().getConnection().createStatement().executeUpdate("delete from orders where id='" + id + "'")>0;
    }
}
