package controller.admin.orderManagement.orderManagementBasicWindow;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.admin.orderManagement.OrderManagementWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.order.Order;
import model.order.OrderDetails;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class OrderManagementBasicWindowController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearchDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn colCusIdMain;

    @FXML
    private TableColumn colDateMain;

    @FXML
    private TableColumn colDiscount;

    @FXML
    private TableColumn colIdMain;

    @FXML
    private TableColumn colItemId;

    @FXML
    private TableColumn colOrderId;

    @FXML
    private TableColumn colPaymentMethodMain;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colTotalMain;

    @FXML
    private TableColumn colUnitPtrice;

    @FXML
    private TableView tblBasicDetail;

    @FXML
    private JFXTextField tctCusId;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtEmpId;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtPayMethod;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtTime;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private TableView tblOrderDetails;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(!txtId.getText().isEmpty()){
            try{
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"do you want to delete order?");
                alert.setHeaderText("");
                Optional<ButtonType>buttonType=alert.showAndWait();
                if(buttonType.isPresent()&&buttonType.get().equals(ButtonType.OK)){
                    if(new OrderManagementControll().deleteOrder(txtId.getText())){
                        new Alert(Alert.AlertType.INFORMATION,"Delete success").show();
                        loadOrderkManagementBasicWindow();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"try again").show();
                    }
                }
            }catch (SQLException ex){
                new Alert(Alert.AlertType.ERROR,"can not connect database").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"search Order").show();
        }
    }

    @FXML
    void btnSearchDetailsOnAction(ActionEvent event) {
        if(Pattern.compile("ord[0-9]+$").matcher(txtSearchId.getText()).find()||Pattern.compile("ORD[0-9]+$").matcher(txtSearchId.getText()).find()){
            try {
                Order order=new OrderManagementControll().getOrderDetails(txtSearchId.getText());
                txtId.setText(order.getId());
                tctCusId.setText(order.getLoyaltyCustomerId());
                txtTotal.setText(Double.toString(order.getTotalPrice()));
                txtPayMethod.setText(order.getPaymentMethod());
                txtDate.setText(order.getDate());
                txtTime.setText(order.getTime());
                txtEmpId.setText(order.getEmpId());


                colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
                colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                colUnitPtrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
                colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

                tblOrderDetails.setItems(getOrderDetails(order.getOrderDetailsList()));



            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"can not connect database").show();
            }
        }
    }

    private ObservableList getOrderDetails(List<OrderDetails> orderDetailsList) {
        ObservableList<OrderDetails> list=FXCollections.observableArrayList();
        orderDetailsList.forEach(e->{
           list.add(e);
        });
        return list;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }


    public void loadOrderkManagementBasicWindow(){
        URL resource=this.getClass().getResource("/view/adminPanel/orderManagement/orderManagementBasicWindow/OrderManagementBasicWindow.fxml");
        assert resource!=null;
        try {
            Parent load= FXMLLoader.load(resource);
            OrderManagementWindowController.orderManagementWindowMainPanel.getChildren().clear();
            OrderManagementWindowController.orderManagementWindowMainPanel.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMiniTable();
    }

    private void loadMiniTable(){
        colIdMain.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusIdMain.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colPaymentMethodMain.setCellValueFactory(new PropertyValueFactory<>("method"));
        colDateMain.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotalMain.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            tblBasicDetail.setItems(new OrderManagementControll().getMiniDableModel());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
