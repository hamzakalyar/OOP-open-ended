package com.labtasks.task;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewItemsController {
    @FXML
    private ListView<String> itemsListView;

    private DatabaseConnection databaseConnection;

    public void initialize() {
        databaseConnection = MAIN.getDatabaseConnection();
        loadItems();
    }

    private void loadItems() {
        String sql = "SELECT itemname, itemprice, imageaddress FROM items";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("itemname");
                double price = rs.getDouble("itemprice");
                String imagePath = rs.getString("imageaddress");

                String itemDetails = String.format("Name: %s, Price: %.2f, Image: %s", name, price, imagePath);
                itemsListView.getItems().add(itemDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
