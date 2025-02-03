package controller.admin.addingFormController.employee;

import DBConnection.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.admin.stockManagement.stockManagementBasicWindow.StockManagementBasicWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeAddingFormController implements Initializable {

    @FXML
    private DatePicker bpBirthday;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox cmbDepId;

    @FXML
    private JFXComboBox cmbGender;

    @FXML
    private JFXComboBox cmbPosition;

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private DatePicker dpAssigningDate;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtTpNo;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            Connection connection=null;
            try {
                connection= DBConnection.getDbConnection().getConnection();
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into `employee` values(?,?,?,?,?,?,?,?,?,?,?,?)");

                preparedStatement.setObject(1,txtId.getText());
                preparedStatement.setObject(2,cmbTitle.getValue().toString());
                preparedStatement.setObject(3,txtName.getText());
                preparedStatement.setObject(4,cmbGender.getValue().toString());
                preparedStatement.setObject(5,cmbDepId.getValue().toString());
                preparedStatement.setObject(6,cmbPosition.getValue().toString());
                preparedStatement.setObject(7,bpBirthday.getValue().toString());
                preparedStatement.setObject(8,txtAddress.getText());
                preparedStatement.setObject(9,Double.parseDouble(txtSalary.getText()));
                preparedStatement.setObject(10,txtEmail.getText());
                preparedStatement.setObject(11,txtTpNo.getText());
                preparedStatement.setObject(12,dpAssigningDate.getValue().toString());


                if(preparedStatement.executeUpdate()>0){

                    if(connection.createStatement().executeUpdate("update `empidcounter` set counter=counter+1 where id=1")>0){

                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION,"added success").show();

                        return;
                    }
                }

                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"try again").show();
            }finally {
                connection.setAutoCommit(true);
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String >title= FXCollections.observableArrayList("Mr","Miss","Mrs");
        cmbTitle.setItems(title);

        ObservableList<String >gender= FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(gender);

        ObservableList<String >dep= FXCollections.observableArrayList("DEPT1000","DEPT1001","DEPT1002");
        cmbDepId.setItems(dep);

        ObservableList<String >position= FXCollections.observableArrayList("Manager","Cashier","Stock Keeper","Helper");
        cmbPosition.setItems(position);

        txtId.setText("EMP"+setEmpmId());

    }

    private String setEmpmId() {
        try {
            ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select counter from empidcounter where id=1");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
