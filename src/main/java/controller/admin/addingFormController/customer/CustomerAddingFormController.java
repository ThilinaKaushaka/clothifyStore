package controller.admin.addingFormController.customer;

import DBConnection.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.admin.placeOrder.placeOrderBasicWindow.PlaceOrderBasicWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerAddingFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTpNo;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            boolean b = new CustomerAddingControll().addCustomer(txtId.getText(), txtName.getText(), txtEmail.getText(), txtTpNo.getText());
            if (b){
                new Alert(Alert.AlertType.INFORMATION,"added success").show();
                PlaceOrderBasicWindowController.addCustomerForm.close();
            }else {
                new Alert(Alert.AlertType.ERROR,"tey again").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        PlaceOrderBasicWindowController.addCustomerForm.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText("ORD"+setCustomerId());
    }

    private String setCustomerId() {
        try {
            ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select counter from customeridcounter where id=1");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
