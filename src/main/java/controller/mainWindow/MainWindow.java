package controller.mainWindow;

import DBConnection.DBConnection;
import controller.logInForm.LogInFormController;
import controller.logInForm.logInFormSide.LogInFormSide;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import starter.Starter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {



    @FXML
    private   AnchorPane pnlMain;

    @FXML
    private   AnchorPane pnlSide;

    public static AnchorPane mainPanal;
    public static  AnchorPane sidePanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPanal=pnlMain;
        sidePanel=pnlSide;
        new LogInFormSide().loadLogInFormSidePanel();
        new LogInFormController().loadLogInForm();
    }

/*
    private  void  setSidePanel(){
        URL resoure=this.getClass().getResource("/view/logInFrom/LogInFormSide.fxml");
        assert  resoure!=null;
        Parent load=null;
        try {
            load=FXMLLoader.load(resoure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pnlSide.getChildren().clear();
        pnlSide.getChildren().add(load);
        Starter.mainWindow.setTitle("Log In");



    }



    private void setLogInForm(){
        URL resoure=this.getClass().getResource("/view/logInFrom/LogInForm.fxml");
        assert resoure!=null;
        Parent load = null;
        try {
            load = load = FXMLLoader.load(resoure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        pnlMain.getChildren().clear();
        pnlMain.getChildren().add(load);
    }

 */



}
