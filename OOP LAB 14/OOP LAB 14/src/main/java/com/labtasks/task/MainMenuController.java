package com.labtasks.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    protected void onViewItemsClick() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/labtasks/task/ViewItems.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            Stage stage = MAIN.getPrimaryStage();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onAddItemsClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/labtasks/task/AddItems.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            Stage stage = MAIN.getPrimaryStage();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
