package DBConnection;

import javafx.scene.control.Alert;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static DBConnection instance;

    @Getter
    private Connection connection;
    private DBConnection() throws SQLException {

        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/clothifystore","root","13345");

    }

    public static DBConnection getDbConnection() throws SQLException {
        return instance==null?instance=new DBConnection():instance;
    }
}
