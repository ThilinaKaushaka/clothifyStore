package controller.admin.stockManagement;

import controller.admin.stockManagement.stockManagementBasicWindow.StockManagementBasicWindowController;
import controller.mainWindow.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockManagementWindowController implements Initializable {

    @FXML
    private AnchorPane pnlStockManagementMain;

    @FXML
    private AnchorPane pnlStockManagementTop;




    public static AnchorPane stockManagementWindowTopPanel;
    public static AnchorPane stockManagementWindowMainPanel;

    public void loadStockManagementWindow(){
        URL resours=this.getClass().getResource("/view/adminPanel/stockManagement/StockManagementWindow.fxml");
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
        stockManagementWindowMainPanel=pnlStockManagementMain;
        stockManagementWindowTopPanel=pnlStockManagementTop;

        new StockManagementBasicWindowController().loadStockManagementBasicWindow();


    }
}
