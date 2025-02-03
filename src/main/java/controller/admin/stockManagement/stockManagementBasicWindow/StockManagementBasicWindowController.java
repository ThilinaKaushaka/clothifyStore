package controller.admin.stockManagement.stockManagementBasicWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.admin.placeOrder.placeOrderBasicWindow.PlaceOrderBasicControll;
import controller.admin.stockManagement.StockManagementWindowController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.item.Item;
import model.item.ItemMiniTableModel;

import java.io.IOException;
import java.net.URL;
import java.security.AllPermission;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class StockManagementBasicWindowController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearchDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn colCategory;

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDiscount;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableView tblBasicDetail;





    @FXML
    private JFXTextField txtColour;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtIBtand;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemcode;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private AnchorPane pnlOption;

    @FXML
    private JFXTextField txtSize;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtWareHouseId;

    @FXML
    private AnchorPane pnlConformBox;

    @FXML
    private JFXButton btnCancel_;

    @FXML
    private JFXButton btnConform;

    @FXML
    private JFXComboBox cmbCategory;

    @FXML
    private JFXComboBox cmbCategoryID;

    public static Stage addItemForm;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Stage stage=new Stage();
        addItemForm=stage;
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminPanel/addingForms/AddItemForm.fxml"))));
            stage.show();
            stage.setTitle("Add Item");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(!txtItemcode.getText().isEmpty()){
           Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do you want to Delete that Item?");
           alert.setTitle("Delete Item");
           alert.setHeaderText("");
            Optional<ButtonType> buttonType=alert.showAndWait();
            if(buttonType.isPresent()&&buttonType.get().equals(ButtonType.OK)) {
                try {
                    if (new StockManagementControll().deleteItem(txtItemcode.getText())) {
                        new Alert(Alert.AlertType.INFORMATION, "Item Delete success").show();
                        loadStockManagementBasicWindow();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "can not delete item").show();
                }

            }
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnConformOnAction(ActionEvent event) {
        try {
            if(new StockManagementControll().updateItem(new Item(txtItemcode.getText(), txtItemName.getText(), cmbCategoryID.getValue().toString(), cmbCategory.getValue().toString(), txtIBtand.getText(), txtColour.getText(), txtSize.getText(), Double.parseDouble(txtDiscount.getText()), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtQty.getText()), txtSupplierId.getText(), txtWareHouseId.getText()))){
                new Alert(Alert.AlertType.INFORMATION,"update successfully").show();
                loadStockManagementBasicWindow();
            }else {
                new Alert(Alert.AlertType.ERROR,"try again").show();
            }
        }catch (SQLException ex){
            new Alert(Alert.AlertType.ERROR,"can not update item").show();
        }
    }



    @FXML
    void btnSearchDetailsOnAction(ActionEvent event) {
        if(Pattern.compile("[item ITEM][0-9]+$").matcher(txtSearchId.getText()).find()){

            try {
                Item item= new StockManagementControll().getItemDetails(txtSearchId.getText());
                if(item!=null){
                    showTextFields();
                    pnlOption.setVisible(true);
                    cmbCategory.setVisible(true);
                    cmbCategoryID.setVisible(true);

                    txtItemcode.setText(item.getItemCode());
                    txtItemName.setText(item.getName());
                    cmbCategoryID.setValue(item.getCategoryId());
                    cmbCategory.setValue(item.getCategoryName());
                    txtIBtand.setText(item.getBrand());
                    txtColour.setText(item.getColour());
                    txtSize.setText(item.getSize());
                    txtDiscount.setText(Double.toString(item.getDiscount()));
                    txtPrice.setText(Double.toString(item.getPrice()));
                    txtQty.setText(Integer.toString(item.getQty()));
                    txtSupplierId.setText(item.getSupplierId());
                    txtWareHouseId.setText(item.getWareHouseId());


                }else {
                    new Alert(Alert.AlertType.INFORMATION,"not item").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!txtItemcode.getText().isEmpty()){
            pnlConformBox.setVisible(true);
            enableTextFields();





        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR,"please search the item");
            alert.setHeaderText("");
            alert.show();


        }

    }

    @FXML
    void btnViewItemReportOnAction(ActionEvent event) {

    }

    public void loadStockManagementBasicWindow(){
        URL resource=this.getClass().getResource("/view/adminPanel/stockManagement/stockManagementBasicWindow/StockManagementBasicWindow.fxml");
        assert resource!=null;
        try {
            Parent load= FXMLLoader.load(resource);
            StockManagementWindowController.stockManagementWindowMainPanel.getChildren().clear();
            StockManagementWindowController.stockManagementWindowMainPanel.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        desableTextFields();
        hideTextFields();
        loadMiniTable();




        try {
            cmbCategory.setItems(new StockManagementControll().getItemCategory());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"cant load item category").show();
        }

        try {
            cmbCategoryID.setItems(new StockManagementControll().getItemCategoryId());
        }catch (SQLException ex){

        }




        pnlConformBox.setVisible(false);
        pnlOption.setVisible(false);
        cmbCategory.setVisible(false);
        cmbCategoryID.setVisible(false);


        cmbCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                cmbCategoryID.getSelectionModel().select(cmbCategory.getSelectionModel().getSelectedIndex());

            }
        });

        cmbCategoryID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                cmbCategory.getSelectionModel().select(cmbCategoryID.getSelectionModel().getSelectedIndex());

            }
        });





    }

    private void hideTextFields() {
        JFXTextField []ar={txtItemcode, txtItemName,  txtIBtand, txtColour, txtSize, txtDiscount, txtPrice, txtQty, txtSupplierId, txtWareHouseId};
        for (JFXTextField jfxTextField : ar) {
            jfxTextField.setVisible(false);
        }
    }

    private void showTextFields(){
        JFXTextField []ar={txtItemcode, txtItemName, txtIBtand, txtColour, txtSize, txtDiscount, txtPrice, txtQty, txtSupplierId, txtWareHouseId};
        for (JFXTextField jfxTextField : ar) {
            jfxTextField.setVisible(true);
        }
    }

    private void desableTextFields() {
        JFXTextField []ar={txtItemcode, txtItemName,  txtIBtand, txtColour, txtSize, txtDiscount, txtPrice, txtQty, txtSupplierId, txtWareHouseId};
        for (JFXTextField jfxTextField : ar) {
            jfxTextField.setEditable(false);
        }
    }

    private void enableTextFields() {
        JFXTextField []ar={txtItemName,  txtIBtand, txtColour, txtSize, txtDiscount, txtPrice, txtQty, txtSupplierId, txtWareHouseId};
        for (JFXTextField jfxTextField : ar) {
            jfxTextField.setEditable(true);
        }
    }

    private void loadMiniTable(){
        try {
            ObservableList<ItemMiniTableModel> itemMiniTableList=new StockManagementControll().getMiniTableList();
            colCode.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            tblBasicDetail.setItems(itemMiniTableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
