package controller.admin.adminNavigationButton;

import com.jfoenix.controls.JFXButton;
import controller.admin.employeeManagement.EmployeeManagementWindow;
import controller.admin.orderManagement.OrderManagementWindowController;
import controller.admin.placeOrder.PlaceOrderWindowController;
import controller.admin.stockManagement.StockManagementWindowController;
import controller.logInForm.LogInFormController;
import controller.logInForm.logInFormSide.LogInFormSide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import starter.Starter;


import java.net.URL;
import java.util.ResourceBundle;

public class AdminNavigationButton implements Initializable {

    @FXML
    private JFXButton btnEmployeeManagement;

    @FXML
    private JFXButton btnOrderManagement;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnSellingReport;

    @FXML
    private JFXButton btnStockManagement;



    private ImageView icon;

    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {

        new EmployeeManagementWindow().loadEmployeeManagementWindow();
        Starter.mainWindow.setTitle("Employee Management");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        new LogInFormSide().loadLogInFormSidePanel();
        new LogInFormController().loadLogInForm();
    }

    @FXML
    void btnOrderManagementOnAction(ActionEvent event) {
        new OrderManagementWindowController().loadOrderManagementWindow();
        Starter.mainWindow.setTitle("Order Management");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        new PlaceOrderWindowController().loadPlaceOrderWindow();
        Starter.mainWindow.setTitle("Place Order");
    }

    @FXML
    void btnSellingReportOnAction(ActionEvent event) {

    }

    @FXML
    private Label lblEmployeeId;

    @FXML
    void btnStockManagementOnAction(ActionEvent event) {
        new StockManagementWindowController().loadStockManagementWindow();
        Starter.mainWindow.setTitle("Stock Management");
    }

    public static Label employeeIdLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeIdLabel=lblEmployeeId;

    }



}
