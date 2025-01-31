package controller.admin.placeOrder;

import controller.admin.placeOrder.placeOrderBasicWindow.PlaceOrderBasicWindowController;
import controller.mainWindow.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderWindowController implements Initializable {

    @FXML
    private AnchorPane pnlPlaceOrderMain;

    @FXML
    private AnchorPane pnlPlaceOrderTop;





    public static AnchorPane placeOrderMainPanel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeOrderMainPanel=pnlPlaceOrderMain;

        new PlaceOrderBasicWindowController().loadPlaceOrderBasicWindow();
    }


    public void loadPlaceOrderWindow(){
        URL resoure=this.getClass().getResource("/view/adminPanel/placeOrder/PlaceOrderWindow.fxml");
        assert  resoure!=null;
        try {
            Parent load= FXMLLoader.load(resoure);
            MainWindow.mainPanal.getChildren().clear();
            MainWindow.mainPanal.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
