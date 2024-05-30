package com.labtasks.task;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MAIN extends Application
{

    private static Stage primaryStage;
    private static DatabaseConnection databaseConnection;

    @Override
    public void start(Stage stage) throws IOException
    {
        primaryStage = stage;
        databaseConnection = new DatabaseConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(AddItemsController.class.getResource("/com/labtasks/task/AddItems.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static DatabaseConnection getDatabaseConnection()
    {
        return databaseConnection;
    }
}
