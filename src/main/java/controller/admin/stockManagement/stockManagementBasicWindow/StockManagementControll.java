package controller.admin.stockManagement.stockManagementBasicWindow;

import DBConnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.item.*;

import java.sql.PreparedStatement;
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

    public ItemDetails getItemDetailsToSearchId(String id) throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("SELECT i.id AS itemId,i.name AS itemName,i.categoryId,ic.name AS categoryName,i.brand,i.colour,i.size,i.discount,i.price,i.qty, i.supplierId, i.wareHouseId FROM item i JOIN itemCategory ic ON i.categoryId = ic.id WHERE i.id = '"+id+"';");
        return resultSet.next()?new ItemDetails(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(7),
                resultSet.getInt(10),
                resultSet.getDouble(9),
                resultSet.getDouble(8)
        ):null;
    }

    public Item getItemDetails(String text) throws SQLException {
        ResultSet resultSetItem = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select * from item where id='" + text + "'");
        if(resultSetItem.next()){

            ResultSet resultSetCName = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select `name` from itemcategory where id='" + resultSetItem.getString(3) + "'");
            resultSetCName.next();

            return new Item(
                    resultSetItem.getString(1),
                    resultSetItem.getString(2),
                    resultSetItem.getString(3),
                    resultSetCName.getString(1),
                    resultSetItem.getString(4),
                    resultSetItem.getString(5),
                    resultSetItem.getString(6),
                    resultSetItem.getDouble(7),
                    resultSetItem.getDouble(8),
                    resultSetItem.getInt(9),
                    resultSetItem.getString(10),
                    resultSetItem.getString(11)

            );
        }
        return null;
    }

    public ObservableList<ItemMiniTableModel> getMiniTableList() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select id,name,categoryId,qty,discount,price from item");
        ObservableList<ItemMiniTableModel> miniTableList = FXCollections.observableArrayList();
        while (resultSet.next())miniTableList.add(new ItemMiniTableModel(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getDouble(5),resultSet.getDouble(6)));
        return miniTableList;
    }

    public boolean deleteItem(String id) throws SQLException {
        return DBConnection.getDbConnection().getConnection().createStatement().executeUpdate("delete from item where id='" + id + "'")>0;

    }

    public Boolean updateItem(Item item) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getDbConnection().getConnection().prepareStatement("update item set name=?, categoryId=?, brand=?, colour=?, size=?, discount=?, price=?, qty=?, supplierId=?, wareHouseId=? where id=?");

        preparedStatement.setObject(1,item.getName());
        preparedStatement.setObject(2,item.getCategoryId());
        preparedStatement.setObject(3,item.getBrand());
        preparedStatement.setObject(4,item.getColour());
        preparedStatement.setObject(5,item.getSize());
        preparedStatement.setObject(6,item.getDiscount());
        preparedStatement.setObject(7,item.getPrice());
        preparedStatement.setObject(8,item.getQty());
        preparedStatement.setObject(9,item.getSupplierId());
        preparedStatement.setObject(10,item.getWareHouseId());
        preparedStatement.setObject(11,item.getItemCode());

        return  preparedStatement.executeUpdate()>0;

    }

    public ObservableList<String> getItemCategoryId() throws SQLException {
        ResultSet resultSet = DBConnection.getDbConnection().getConnection().createStatement().executeQuery("select id from itemcategory");
        ObservableList<String >categoryIdList=FXCollections.observableArrayList();
        while (resultSet.next())categoryIdList.add(resultSet.getString(1));
        return categoryIdList;


    }
}
