package controller.admin.addingFormController.customer;

import DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerAddingControll {

    public boolean addCustomer(String id, String name, String email, String tpNo) throws SQLException {
        Connection connection = DBConnection.getDbConnection().getConnection();


        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into `loyaltycustomer` values(?,?,?,?,?)");
            preparedStatement.setObject(1,id);
            preparedStatement.setObject(2,name);
            preparedStatement.setObject(3,500);
            preparedStatement.setObject(4,email);
            preparedStatement.setObject(5,tpNo);

            if(preparedStatement.executeUpdate()>0){
                if(connection.createStatement().executeUpdate("update `customeridcounter` set counter=counter+1 where id=1")>0){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;


        }finally {
            connection.setAutoCommit(true);
        }


    }
}
