package controller.admin.stockManagement.stockManagementBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.item.ItemDetailsByItemCode;
import model.item.ItemDetailsByMiniTable;
import model.item.ItemDetailsBySize;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockManagementControll {







    public ObservableList<String> getItemCategory() throws SQLException {
        ObservableList<String> itemCategory = FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select name from itemCategory");
        while (resultSet.next())itemCategory.add(resultSet.getString(1));
        return itemCategory;
    }

    public ObservableList<String> getItemCodes(String category) throws SQLException {
        ObservableList<String> itemCodes=FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("SELECT i.id AS itemCode FROM item i JOIN itemCategory c ON i.categoryId = c.id WHERE c.name = '" + category + "'");
        while (resultSet.next())itemCodes.add(resultSet.getString(1));
        return itemCodes;

    }

    public ObservableList<String> getItemNames(String category) throws SQLException {
        ObservableList<String> itemNames=FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("SELECT i.name AS itemName FROM item i JOIN itemCategory c ON i.categoryId = c.id WHERE c.name = '" + category + "'");
        while (resultSet.next())itemNames.add(resultSet.getString(1));
        return itemNames;
    }

    public ObservableList getItemSize(String itemName) throws SQLException {
        ObservableList<String> sizeList=FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select distinct size from item where name='" + itemName + "'");
        while (resultSet.next())sizeList.add(resultSet.getString(1));
        return sizeList;
    }

    public ItemDetailsBySize getItemDetails(String name, String size) throws SQLException {
        ResultSet resultSet =DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select qty,brand,discount,price from item where size='" + size + "' AND name ='" + name + "'");


        return resultSet.next()?new ItemDetailsBySize(resultSet.getInt(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4)):null;
    }

    public ItemDetailsByItemCode getItemDetailsByItemCode(String itemId) throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select size,qty,brand,discount,price from item where id='" + itemId + "'");
        return resultSet.next()?new ItemDetailsByItemCode(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getDouble(5)):null;
    }

    public ObservableList<ItemDetailsByMiniTable> getItemDetailsToMiniTable(String category) throws SQLException {
        ObservableList<ItemDetailsByMiniTable> miniTableDataArray=FXCollections.observableArrayList();
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("SELECT i.id, i.`name`, i.size,i.price FROM item i JOIN itemCategory c ON i.categoryId = c.id WHERE c.name = '"+category+"'");
        while (resultSet.next())miniTableDataArray.add(new ItemDetailsByMiniTable(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4)));
        return miniTableDataArray;
    }
}
