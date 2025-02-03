package controller.admin.employeeManagement.employeManagementBasicWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.admin.employeeManagement.EmployeeManagementWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.employee.EmployeeDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;

public class EmployeeManagementBasicWindow implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearchDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox cmbGender;

    @FXML
    private JFXComboBox cmbPosition;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPosition;

    @FXML
    private DatePicker dpAssigningDate;

    @FXML
    private DatePicker dpBirthDay;

    @FXML
    private TableView tblBasicDetail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtDepId;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtTpNo;

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private JFXButton btnCancel_;

    @FXML
    private JFXButton btnConform;

    @FXML
    private AnchorPane pnlConformBox;

    @FXML
    private AnchorPane pnlOption;

    public static Stage addEmployeeForm;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Stage stage=new Stage();
        addEmployeeForm=stage;

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminPanel/addingForms/EmployeeAddingForm.fxml"))));
            stage.show();
            stage.setTitle("Add Employee Form");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadEmployeeManagementBasicWindow();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do you want to deete Employee ? ");
            alert.setHeaderText("");
            Optional<ButtonType> buttonType=alert.showAndWait();
            if(buttonType.isPresent()&& buttonType.get().equals(ButtonType.OK)){
                try {
                    if(new EmployeeManagementControll().deleteEmployee(txtId.getText())){
                        new Alert(Alert.AlertType.INFORMATION,"Employee delete success").show();
                        loadEmployeeManagementBasicWindow();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"try again").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"can not connect DataBase").show();
                }
            }
        }
    }

    @FXML
    void btnSearchDetailsOnAction(ActionEvent event) {
        if(Pattern.compile("EMP[0-9]+$").matcher(txtSearchId.getText()).find() || Pattern.compile("emp[0-9]+$").matcher(txtSearchId.getText()).find() ){
            EmployeeDetails employeeDetails;
            try {
                employeeDetails=new EmployeeManagementControll().getEmployeeDetails(txtSearchId.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (employeeDetails!=null){
                showControls();
                txtId.setText(employeeDetails.getEmpId());
                cmbTitle.setValue((employeeDetails.getTitle()));
                txtName.setText(employeeDetails.getName());
                cmbGender.setValue(employeeDetails.getGender());
                txtDepId.setText(employeeDetails.getDpId());
                cmbPosition.getSelectionModel().select(employeeDetails.getPosition());
                dpBirthDay.setValue(LocalDate.parse(employeeDetails.getBirthDay()));
                txtAddress.setText(employeeDetails.getAddress());
                txtSalary.setText(Double.toString(employeeDetails.getSalary()));
                txtEmail.setText(employeeDetails.getEmail());
                txtTpNo.setText(employeeDetails.getTpNo());
                dpAssigningDate.setValue(LocalDate.parse(employeeDetails.getAssigningDate()));

                pnlOption.setVisible(true);

            }else {
                new Alert(Alert.AlertType.ERROR,"Employee is not ").show();
            }

        }



    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        pnlConformBox.setVisible(true);
        editbleOn();
    }

    @FXML
    void btnViewEmployeeReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnConformOnAction(ActionEvent event) {
       try {
           if(new EmployeeManagementControll().updateDetails(new EmployeeDetails(txtId.getText(), cmbTitle.getValue().toString(), txtName.getText(), cmbGender.getValue().toString(), txtDepId.getText(), cmbPosition.getValue().toString(), dpBirthDay.getValue().toString(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()), txtEmail.getText(), txtTpNo.getText(), dpAssigningDate.getValue().toString()))){
                new Alert(Alert.AlertType.INFORMATION,"update successfully").show();
                loadEmployeeManagementBasicWindow();
           }else{
               new Alert(Alert.AlertType.ERROR,"try again").show();
           }

       } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"can not update").show();
       }


    }



    public void loadEmployeeManagementBasicWindow(){
        URL resource=this.getClass().getResource("/view/adminPanel/employeeManagement/employeeManagementBasicWindow/EmployeeManagementBasicWindow.fxml");
        assert resource!=null;
        try {
            Parent load= FXMLLoader.load(resource);
            EmployeeManagementWindow.employeeManagementWindowMainPanel.getChildren().clear();
            EmployeeManagementWindow.employeeManagementWindowMainPanel.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCGender();
        setTitle();
        setPosition();
        hideControls();
        editbleOff();
        loadMiniTable();

        pnlOption.setVisible(false);
        pnlConformBox.setVisible(false);

    }

    private void loadMiniTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        try {
            tblBasicDetail.setItems(new EmployeeManagementControll().getEmployeeDetailsList());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not load mini table").show();
        }
    }

    private void setCGender(){
        ObservableList<String >genderList= FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(genderList);
    }

    private void  setTitle(){
        ObservableList<String>titleList=FXCollections.observableArrayList("Mr","Mrs","Miss");
        cmbTitle.setItems(titleList);
    }

    private void setPosition(){
        ObservableList<String>positionList=FXCollections.observableArrayList("Manager","Cashier","Stock Keeper","Helper");
        cmbPosition.setItems(positionList);
    }



    private void hideControls(){
        Control [] controlsList={cmbGender,cmbPosition,dpAssigningDate,dpBirthDay,txtAddress,txtDepId,txtEmail,txtId,txtName,txtSalary,txtTpNo,cmbTitle};

        for(Control control:controlsList)control.setVisible(false);
    }

    private void showControls(){
        Control [] controlsList={cmbGender,cmbPosition,dpAssigningDate,dpBirthDay,txtAddress,txtDepId,txtEmail,txtId,txtName,txtSalary,txtTpNo,cmbTitle};

        for(Control control:controlsList)control.setVisible(true);
    }

    private void editbleOff(){
        txtAddress.setEditable(false);txtDepId.setEditable(false);txtEmail.setEditable(false);txtId.setEditable(false);txtName.setEditable(false);txtSalary.setEditable(false);txtTpNo.setEditable(false);
    }

    private void editbleOn(){
        txtAddress.setEditable(true);txtDepId.setEditable(true);txtEmail.setEditable(true);txtName.setEditable(true);txtSalary.setEditable(true);txtTpNo.setEditable(true);
    }







}
