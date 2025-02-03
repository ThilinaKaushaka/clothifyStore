package controller.admin.addingFormController.item;

import DBConnection.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.admin.employeeManagement.employeManagementBasicWindow.EmployeeManagementBasicWindow;
import controller.admin.stockManagement.stockManagementBasicWindow.StockManagementBasicWindowController;
import controller.admin.stockManagement.stockManagementBasicWindow.StockManagementControll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox cmbCategory;

    @FXML
    private JFXComboBox cmbSize;

    @FXML
    private JFXTextField txtBrand;

    @FXML
    private JFXTextField txtColour;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSupId;

    @FXML
    private JFXTextField txtWareHouseId;

    @FXML
    void btnAddOnAction(ActionEvent event) {


        try {
            Connection connection=null;
            try {
                connection=DBConnection.getDbConnection().getConnection();
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into `item` values(?,?,?,?,?,?,?,?,?,?,?)");
                preparedStatement.setObject(1,txtId.getText());
                preparedStatement.setObject(2,txtName.getText());
                preparedStatement.setObject(3,cmbCategory.getValue().toString());
                preparedStatement.setObject(4,txtBrand.getText());
                preparedStatement.setObject(5,txtColour.getText());
                preparedStatement.setObject(6,cmbSize.getValue().toString());
                preparedStatement.setObject(7,Double.parseDouble(txtDiscount.getText()));
                preparedStatement.setObject(8,Double.parseDouble(txtPrice.getText()));
                preparedStatement.setObject(9,Integer.parseInt(txtQty.getText()));
                preparedStatement.setObject(10,txtSupId.getText());
                preparedStatement.setObject(11,txtWareHouseId.getText());

                if(preparedStatement.executeUpdate()>0){
                    System.out.println("1");
                    if(connection.createStatement().executeUpdate("update `itemidcounter` set counter=counter+1 where id=1")>0){
                        System.out.println("2");
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION,"added success").show();
                        EmployeeManagementBasicWindow.addEmployeeForm.close();
                        return;
                    }
                }

                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"try again").show();
            }finally {
                connection.setAutoCommit(true);
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        EmployeeManagementBasicWindow.addEmployeeForm.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            cmbCategory.setItems((ObservableList) new StockManagementControll().getItemCategoryId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> size= FXCollections.observableArrayList("XS","S","M","L","XL","XXL","XXL");
        cmbSize.setItems(size);

        txtId.setText("ITEM"+ setItemId());
    }

    private String setItemId() {
        try {
            ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select counter from itemidcounter where id=1");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
