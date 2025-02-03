package controller.logInForm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.admin.adminNavigationButton.AdminNavigationButton;
import controller.admin.basicWindow.BasicWindowController;
import controller.mainWindow.MainWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import model.user.UserFind;
import model.user.UserLogIn;
import starter.Starter;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogInFormController implements Initializable {

    @FXML
    private JFXButton btnShowPassword;

    @FXML
    private JFXTextField txtShowPassword;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnLogIn;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnxx;

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {



        boolean validEmail=isValidEmail();

        if (validEmail && !txtPassword.getText().equals("")){
            try {
                UserFind userFind=new LogInController().LogIn(new UserLogIn(txtEmail.getText(),txtPassword.getText()));
                System.out.println(userFind);
                if (userFind!=null){
                    switch (userFind.getAccessLevel()){
                        case ADMIN:
                            loadAdminWindow(userFind.getId());
                            new BasicWindowController().loadBasicWindow();

                            break;

                        case CASHIER:

                            break;

                        case STOCK_MANAGER:

                            break;

                        default:
                            new Alert(Alert.AlertType.ERROR,"try again").show();
                    }


                }else {

                    new Alert(Alert.AlertType.INFORMATION,"Enter valid email and password").show();
                }




            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"can not find user").show();
            }
        }

    }

    private void loadAdminWindow(String id){
        loadAdminNavigationButton(id);
        new BasicWindowController().loadBasicWindow();
    }

    private void loadAdminNavigationButton(String id){
        URL resource=this.getClass().getResource("/view/adminPanel/adminNavigationButton.fxml");
        assert resource !=null;

        try {
            Parent load= FXMLLoader.load(resource);
            MainWindow.sidePanel.getChildren().clear();
            MainWindow.sidePanel.getChildren().add(load);
            Starter.mainWindow.setTitle("Administrator Panel");

            AdminNavigationButton.employeeIdLabel.setText(id);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    private boolean isValidEmail(){

        return Pattern.compile("[a-z A-Z 0-9]+@[a-z A-Z 0-9]+\\.[a-zA-Z]{2,}").matcher(txtEmail.getText()).matches();
    }




    private boolean Lc=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtShowPassword.setVisible(false);

    }

    @FXML
    void btnShowPasswordOnAction(ActionEvent event) {
        if (Lc){
            txtPassword.setVisible(true);
            txtShowPassword.setVisible(false);
            Lc=false;

        }else {
            if (txtShowPassword.getText()!=""){
                txtShowPassword.setText(txtPassword.getText());
            }
            txtShowPassword.setVisible(true);
            txtPassword.setVisible(false);
            Lc=true;
        }
    }

    @FXML
    void txtShowPasswordOnKeyType(KeyEvent event) {
        txtPassword.setText(txtShowPassword.getText());
    }


    public void loadLogInForm(){
        URL resoure=this.getClass().getResource("/view/logInFrom/LogInForm.fxml");
        assert resoure!=null;
        Parent load = null;
        try {
            load = load = FXMLLoader.load(resoure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MainWindow.mainPanal.getChildren().clear();
        MainWindow.mainPanal.getChildren().add(load);
    }




}
