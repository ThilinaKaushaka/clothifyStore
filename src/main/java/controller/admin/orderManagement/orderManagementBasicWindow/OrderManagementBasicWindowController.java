package controller.admin.orderManagement.orderManagementBasicWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.admin.orderManagement.OrderManagementWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;

public class OrderManagementBasicWindowController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearchDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colArrivalToDay;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableView<?> tblBasicDetail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPosition;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }


    public void loadOrderkManagementBasicWindow(){
        URL resource=this.getClass().getResource("/view/adminPanel/orderManagement/orderManagementBasicWindow/OrderManagementBasicWindow.fxml");
        assert resource!=null;
        try {
            Parent load= FXMLLoader.load(resource);
            OrderManagementWindowController.orderManagementWindowMainPanel.getChildren().clear();
            OrderManagementWindowController.orderManagementWindowMainPanel.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
