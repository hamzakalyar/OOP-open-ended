package com.labtasks.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddItemsController {
    @FXML
    private TextField product;
    @FXML
    private TextField price;
    @FXML
    private ImageView imageView;
    private File imageFile;

    @FXML
    private Label statusMessage;

    @FXML
    private TilePane tilePane;

    private DatabaseConnection databaseConnection;

    public void initialize() {
        databaseConnection = MAIN.getDatabaseConnection();
        displayProductsInTilePane();
    }

    @FXML
    protected void onSubmitButtonClick() {
        try {
            double productPrice = Double.parseDouble(price.getText());
            insertProduct(new Product(product.getText(), productPrice, imageFile));
            statusMessage.setText("Product submitted successfully!");

            // Clear input fields for the next entry
            clearInputFields();

            // Refresh the display
            displayProductsInTilePane();
        } catch (NumberFormatException e) {
            statusMessage.setText("Invalid price format!");
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setText("Error submitting product!");
        }
    }

    @FXML
    protected void onSelectImageClick() {
        imageFile = selectImageFile();
        if (imageFile != null) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    private File selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fileChooser.showOpenDialog(new Stage());
    }

    private void insertProduct(Product product) {
        String sql = "INSERT INTO items (itemname, itemprice, imageaddress) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getImagePath());
            pstmt.executeUpdate();

            System.out.println("Product inserted: " + product.getName() + ", " + product.getPrice() + ", " + product.getImagePath());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProductsInTilePane() {
        tilePane.getChildren().clear();

        for (Product product : getProductsFromDatabase()) {
            VBox productBox = new VBox();
            productBox.setAlignment(Pos.CENTER);
            productBox.setSpacing(5);

            Label nameLabel = new Label(product.getName());
            Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));

            ImageView productImageView = new ImageView();
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                productImageView.setImage(new Image(new File(product.getImagePath()).toURI().toString()));
            }
            productImageView.setFitHeight(100);
            productImageView.setFitWidth(100);

            productBox.getChildren().addAll(productImageView, nameLabel, priceLabel);
            tilePane.getChildren().add(productBox);
        }
    }

    private List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT itemname, itemprice, imageaddress FROM items";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("itemname");
                double price = rs.getDouble("itemprice");
                String imagePath = rs.getString("imageaddress");

                products.add(new Product(name, price, new File(imagePath)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @FXML
    protected void onViewItemsClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/labtasks/task/ViewItems.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);

            Stage stage = MAIN.getPrimaryStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view items!");
        }
    }

    @FXML
    protected void onCloseButtonClick() {
        Stage stage = MAIN.getPrimaryStage();
        stage.close();
    }

    private void clearInputFields() {
        product.clear();
        price.clear();
        imageView.setImage(null);
        imageFile = null;
    }
}
