package controller.logInForm;

import DBConnection.DBConnection;
import javafx.scene.control.Alert;
import model.user.AccessLevel;
import model.user.UserFind;
import model.user.UserLogIn;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.*;

public class LogInController implements LogInFormService{

    @Override
    public UserFind LogIn(UserLogIn userLogIn) throws SQLException {

        Statement statement = DBConnection.getDbConnection().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE email='"+userLogIn.getEmail()+"' AND userPassWord='"+userLogIn.getPassword()+"'");

        while (resultSet.next()){

            return new UserFind(
                    resultSet.getString(4).equals("ADMIN")?AccessLevel.ADMIN:
                    resultSet.getString(4).equals("CASHIER")?AccessLevel.CASHIER:
                    AccessLevel.STOCK_MANAGER
            );

        }

        return null;
    }



}
