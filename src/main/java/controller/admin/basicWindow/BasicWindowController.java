package controller.admin.basicWindow;

import controller.admin.placeOrder.PlaceOrderWindowController;
import controller.mainWindow.MainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class BasicWindowController {
    public void loadBasicWindow(){
        URL resource= this.getClass().getResource("/view/BasicWindow/BasicWindow.fxml");

        assert resource!=null;

        try {
            Parent load= FXMLLoader.load(resource);
            MainWindow.mainPanal.getChildren().clear();
            MainWindow.mainPanal.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
