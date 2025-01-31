package controller.admin.employeeManagement;

import controller.admin.employeeManagement.employeManagementBasicWindow.EmployeeManagementBasicWindow;
import controller.mainWindow.MainWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementWindow implements Initializable {

    public static AnchorPane employeeManagementWindowMainPanel;
    public static AnchorPane employeeManagementWindowTopPanel;


    @FXML
    private AnchorPane pnlEmployeeManagementMain;

    @FXML
    private AnchorPane pnlEmployeeManagementTop;



    public void loadEmployeeManagementWindow(){
        URL resours=this.getClass().getResource("/view/adminPanel/employeeManagement/EmployeManagementWindow.fxml");
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
        employeeManagementWindowMainPanel=pnlEmployeeManagementMain;
        employeeManagementWindowTopPanel=pnlEmployeeManagementTop;


        new EmployeeManagementBasicWindow().loadEmployeeManagementBasicWindow();
    }
}
