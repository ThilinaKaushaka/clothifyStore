package controller.admin.orderManagement;

import controller.admin.orderManagement.orderManagementBasicWindow.OrderManagementBasicWindowController;
import controller.mainWindow.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderManagementWindowController implements Initializable {

    @FXML
    private AnchorPane pnlOrderManagementMain;

    @FXML
    private AnchorPane pnlOrderManagementTop;


    public static AnchorPane orderManagementWindowTopPanel;
    public static AnchorPane orderManagementWindowMainPanel;

    public void loadOrderManagementWindow(){
        URL resours=this.getClass().getResource("/view/adminPanel/orderManagement/OrderManagementWindow.fxml");
        assert resours!=null;

        try {
            Parent load= FXMLLoader.load(resours);
            MainWindow.mainPanal.getChildren().clear();
            MainWindow.mainPanal.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderManagementWindowMainPanel=pnlOrderManagementMain;
        orderManagementWindowTopPanel=pnlOrderManagementTop;

        new OrderManagementBasicWindowController().loadOrderkManagementBasicWindow();
    }
}
