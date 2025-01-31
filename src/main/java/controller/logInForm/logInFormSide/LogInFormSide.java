package controller.logInForm.logInFormSide;

import controller.mainWindow.MainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import starter.Starter;

import java.io.IOException;
import java.net.URL;

public class LogInFormSide {

    public void loadLogInFormSidePanel(){
        URL resoure=this.getClass().getResource("/view/logInFrom/LogInFormSide.fxml");
        assert  resoure!=null;
        Parent load=null;
        try {
            load= FXMLLoader.load(resoure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainWindow.sidePanel.getChildren().clear();
        MainWindow.sidePanel.getChildren().add(load);
        Starter.mainWindow.setTitle("Log In");
    }

}
