package com.ijse.gdse.carwash;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingScreenView.fxml"))));
        stage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/UserView.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("carwach");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/download(5).jpg")));
            stage.setMaximized(true);

            stage.setScene(value);
        });

        loadingTask.setOnFailed(event -> {
            System.err.println("Failed to load the main layout.");
        });

        new Thread(loadingTask).start();

    }
}