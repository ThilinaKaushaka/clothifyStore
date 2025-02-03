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
        BasicTextEncryptor bte=new BasicTextEncryptor();
        bte.setPassword("123");
        System.out.println(userLogIn);
        System.out.println(bte.encrypt(userLogIn.getPassword()));

        Statement statement = DBConnection.getDbConnection().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM USER WHERE email='"+userLogIn.getEmail()+"'");

        while (resultSet.next()){
            if(bte.decrypt(resultSet.getString(4)).equals(userLogIn.getPassword())){
                return new UserFind(
                        resultSet.getString(1),
                        resultSet.getString(2).equals("ADMIN")?AccessLevel.ADMIN: resultSet.getString(2).equals("CASHIER")?AccessLevel.CASHIER: AccessLevel.STOCK_MANAGER
                );
            }


        }

        return null;
    }



}
