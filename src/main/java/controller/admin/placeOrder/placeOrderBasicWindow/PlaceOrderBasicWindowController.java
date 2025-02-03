package controller.admin.placeOrder.placeOrderBasicWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.admin.adminNavigationButton.AdminNavigationButton;
import controller.admin.orderManagement.orderManagementBasicWindow.OrderManagementControll;
import controller.admin.placeOrder.PlaceOrderWindowController;
import controller.admin.stockManagement.stockManagementBasicWindow.StockManagementControll;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.customer.CustomerBasicDetails;
import model.item.ItemDetails;
import model.item.ItemDetailsByItemCode;
import model.item.ItemDetailsByMiniTable;
import model.item.ItemDetailsBySize;
import model.order.CartDetails;
import model.order.Order;
import model.order.OrderDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderBasicWindowController implements Initializable {
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnCustomerIdSearch;

    @FXML
    private JFXButton btnItemIdSearch;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox cbmCategory;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private JFXComboBox cmbItemName;

    @FXML
    private JFXComboBox cmbPaymentMethod;

    @FXML
    private JFXComboBox cmbItemSize;

    @FXML
    private TableColumn colCartItemBrand;

    @FXML
    private TableColumn colCartItemCode;

    @FXML
    private TableColumn colCartItemDiscount;

    @FXML
    private TableColumn colCartItemName;

    @FXML
    private TableColumn colCartItemQty;

    @FXML
    private TableColumn colCartItemSize;

    @FXML
    private TableColumn colCartSubTotal;

    @FXML
    private TableColumn colCartUnitPrice;

    @FXML
    private TableColumn colItemId;

    @FXML
    private TableColumn colItemName;

    @FXML
    private TableColumn colItemSize;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXTextField txtAvailable;

    @FXML
    private JFXTextField txtSearchCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtIItemQty;

    @FXML
    private JFXTextField txtItemBrand;

    @FXML
    private JFXTextField txtItemCost;

    @FXML
    private JFXTextField txtItemDiscount;

    @FXML
    private JFXTextField txtItemIdSearch;

    @FXML
    private JFXTextField txtTpNo;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private TableView tblItemDetailsMini;

    @FXML
    private TableView tblCart;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderIdTitle;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    void btnRefreshOnAction(ActionEvent event) {
        loadPlaceOrderBasicWindow();
    }

    public static Stage addCustomerForm;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        Stage stage=new Stage();
        addCustomerForm=stage;
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminPanel/addingForms/AddCustomerForm.fxml"))));
            stage.show();
            stage.setTitle("Add Customer Form");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDateAndTime(){

        //-----------------DATE-------------------------
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        lblDate.setText(format);

        //----------------TIME------------------
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,e->{
                    LocalTime now=LocalTime.now();
                    lblTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
                    now.getSecond();
                }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private List<CartDetails> cartItemsList=new ArrayList<>();
    private ObservableList<CartDetails> cartTableItemList=FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        if(!txtItemCost.getText().equals("")&& !txtIItemQty.getText().equals("") && !txtUnitPrice.getText().equals("")){
            colCartItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colCartItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCartItemSize.setCellValueFactory(new PropertyValueFactory<>("size"));
            colCartItemBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
            colCartItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            colCartItemDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colCartUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colCartSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));


            createCartTableItemList();
            tblCart.setItems(cartTableItemList);
            btnRefresh.setVisible(true);
        }

    }

    private void createCartTableItemList() {
        addDataTocartItemList();
        cartTableItemList.clear();
        cartItemsList.forEach(cartItem->{
            cartTableItemList.add(cartItem);
        });

    }

    private void addDataTocartItemList() {
        CartDetails cartDetail=new CartDetails( cmbItemCode.getValue().toString() , cmbItemName.getValue().toString() , cmbItemSize.getValue().toString() , txtItemBrand.getText() , Integer.parseInt(txtIItemQty.getText()) , Double.parseDouble(txtItemDiscount.getText()) , Double.parseDouble(txtUnitPrice.getText()) , Double.parseDouble(txtItemCost.getText()));
        if(cartItemsList.contains(cartDetail)){
            for(CartDetails data:cartItemsList){
                if (data.getItemCode().equals(cartDetail.getItemCode())){
                    data.setQty(data.getQty()+cartDetail.getQty());
                    data.setSubTotal(data.getSubTotal()+cartDetail.getSubTotal());
                }
            }
        }else {
            cartItemsList.add(cartDetail);
        }

        displayTotal();

    }

    private void displayTotal() {
        double tot=0.00;
        for(CartDetails detail:cartItemsList)tot+=detail.getSubTotal();
        lblTotal.setText(Double.toString(tot));


    }

    @FXML
    void btnCustomerIdSearchOnAction(ActionEvent event) {
        try {
            CustomerBasicDetails customerBasicDetails = new PlaceOrderBasicControll().searchCustomerOrderWindow(txtSearchCustomerId.getText());
            if (customerBasicDetails!=null){
                txtCustomerId.setText(customerBasicDetails.getId());
                txtCustomerName.setText(customerBasicDetails.getName());
                txtTpNo.setText(customerBasicDetails.getTpNo());
            }else {
                new Alert(Alert.AlertType.INFORMATION,"customer not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"try again leater").show();
        }
    }

    @FXML
    void btnItemIdSearchOnAction(ActionEvent event) {
        if(Pattern.compile("[item ITEM][0-9]+$").matcher(txtItemIdSearch.getText()).find()){
            try {
                ItemDetails itemDetails=new StockManagementControll().getItemDetailsToSearchId(txtItemIdSearch.getText());
                if (itemDetails!=null){
                    cbmCategory.setValue(itemDetails.getCategoryName());
                    cmbItemCode.setValue(itemDetails.getItemId());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        if(!cartItemsList.isEmpty()){
            List<OrderDetails>orderDetailsList=new ArrayList<>();
            cartItemsList.forEach(cartDetails -> { orderDetailsList.add(new OrderDetails(lblOrderId.getText(), cartDetails.getItemCode(), cartDetails.getQty(), cartDetails.getUnitPrice(), cartDetails.getDiscount()));});

            try {
                if(new PlaceOrderBasicControll().placeOrder (new Order( lblOrderId.getText(),txtCustomerId.getText().equals("")?null:txtCustomerId.getText(), Double.parseDouble(lblTotal.getText()), cmbPaymentMethod.getValue().toString(), lblDate.getText(), lblTime.getText(), AdminNavigationButton.employeeIdLabel.getText(), orderDetailsList))){
                    new Alert(Alert.AlertType.CONFIRMATION,"Order Place").show();
                    new PlaceOrderBasicWindowController().loadPlaceOrderBasicWindow();

                }else {
                    new Alert(Alert.AlertType.ERROR,"try again").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"can not connect database").show();
            }
        }


    }

    @FXML
    void txtItemQtyOnKeyTyped(KeyEvent event) {
        if(Pattern.matches("^-?\\d*\\.?\\d+$",txtIItemQty.getText())) {
            if (Integer.parseInt(txtAvailable.getText()) >= Integer.parseInt(txtIItemQty.getText()) && !txtIItemQty.getText().equals("") && Integer.parseInt(txtIItemQty.getText()) > 0) {
                txtItemCost.setText(Double.toString((Double.parseDouble(txtUnitPrice.getText()) - (Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtItemDiscount.getText()) / 100)) * Integer.parseInt(txtIItemQty.getText())));
            } else {
                txtItemCost.setText("");
            }
        }else {
            txtItemCost.setText("");
        }
    }






    public void loadPlaceOrderBasicWindow(){
        URL resource= this.getClass().getResource("/view/adminPanel/placeOrder/placeOrderBasicWindow/PlaceOrderBasicWindow.fxml");

        assert resource!=null;

        try {
            Parent load= FXMLLoader.load(resource);
            PlaceOrderWindowController.placeOrderMainPanel.getChildren().clear();
            PlaceOrderWindowController.placeOrderMainPanel.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPaymnetMethod();
        desableEditableAccess();
        setItemCategory();
        desableOrderPlaceButton();
        setDateAndTime();

        cbmCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){

                searchItemCodes(newValue.toString());
                searchItemName(newValue.toString());
                redoItemData();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                changeSelecterItemName(cmbItemCode.getSelectionModel().getSelectedIndex());
                diplayItemDetails(newValue.toString());
            }
        });

        cmbItemName.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                changeSelecterItemCode(cmbItemName.getSelectionModel().getSelectedIndex());
                try {
                    cmbItemSize.setItems(new StockManagementControll().getItemSize(cmbItemName.getValue().toString()));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"can not load item size").show();
                }
            }
        });

        cmbItemSize.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    displayItemDetailsChangeSize(new StockManagementControll().getItemDetails(cmbItemName.getValue().toString(),cmbItemSize.getValue().toString()));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"can not display Item details").show();
                }
            }
        });

        cmbPaymentMethod.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                try {
                    lblOrderId.setText(new OrderManagementControll().getNewOrderId());
                    enablePlaceOrderButton();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,"can not load order Id").show();
                }
            }
        });


        btnRefresh.setVisible(false);



    }

    private void desableOrderPlaceButton(){
        btnPlaceOrder.setDisable(true);
    }

    private void enablePlaceOrderButton(){
        btnPlaceOrder.setDisable(false);
    }

    private void redoItemData() {
        if(!txtAvailable.getText().equals("")){
            JFXTextField [] textFieldsArray={txtAvailable,txtItemBrand,txtItemDiscount,txtUnitPrice,txtItemCost,txtIItemQty};
            for(JFXTextField item:textFieldsArray)item.setText("");
            cmbItemSize.getSelectionModel().clearSelection();
        }

    }

    private void diplayItemDetails(String itemId) {
        try {
            ItemDetailsByItemCode itemDetailsByItemCode=new StockManagementControll().getItemDetailsByItemCode(itemId);
            cmbItemSize.getSelectionModel().select(itemDetailsByItemCode.getSize());
            txtAvailable.setText(Integer.toString(itemDetailsByItemCode.getAvailable()));
            txtItemBrand.setText(itemDetailsByItemCode.getBrand());
            txtItemDiscount.setText(Double.toString(itemDetailsByItemCode.getDiscount()));
            txtUnitPrice.setText(Double.toString(itemDetailsByItemCode.getPrice()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not display idem Details").show();
        }
    }

    private void displayItemDetailsChangeSize(ItemDetailsBySize itemDetails) {
        txtAvailable.setText(Integer.toString(itemDetails.getAvailable()));
        txtItemBrand.setText(itemDetails.getBrand());
        txtItemDiscount.setText(Double.toString(itemDetails.getDiscount()));
        txtUnitPrice.setText(Double.toString(itemDetails.getUnidPrice()));
    }

    private void changeSelecterItemCode(int selectedIndex) {
        cmbItemCode.getSelectionModel().select(selectedIndex);
    }

    private void changeSelecterItemName(int selectedIndex) {
        cmbItemName.getSelectionModel().select(selectedIndex);
    }

    private void searchItemName(String category) {
        try {
            ObservableList<String> itemNames=new StockManagementControll().getItemNames(category);
            cmbItemName.setItems(itemNames);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not load item codes").show();
        }
    }


    private void searchItemCodes(String category) {
        try {
            ObservableList<String> itemCodes=new StockManagementControll().getItemCodes(category);
            cmbItemCode.setItems(itemCodes);
            showItemDetailsMiniTable(category);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not load item codes").show();
        }
    }

    private void showItemDetailsMiniTable(String category) {
        ObservableList<ItemDetailsByMiniTable> itemDetailsArray= null;
        try {
            itemDetailsArray = new StockManagementControll().getItemDetailsToMiniTable(category);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can no load mini table").show();
        }
        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblItemDetailsMini.setItems(itemDetailsArray);


    }

    private void setItemCategory(){
        try {
            cbmCategory.setItems(new StockManagementControll().getItemCategory());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not load item category").show();
        }
    }

    private void desableEditableAccess(){
        JFXTextField [] txtArray= {txtCustomerId,txtCustomerName,txtTpNo,txtAvailable,txtItemBrand,txtItemDiscount,txtUnitPrice,txtItemCost};
        for (JFXTextField item:txtArray)item.setEditable(false);
    }



    private void loadPaymnetMethod() {
        try {
            cmbPaymentMethod.setItems(new PlaceOrderBasicControll().getPaymentMethods());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"can not load payment Methods").show();
        }
    }


}
