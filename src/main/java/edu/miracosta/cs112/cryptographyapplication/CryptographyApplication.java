package edu.miracosta.cs112.cryptographyapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CryptographyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CryptographyApplication.class.getResource("StartScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cryptography Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}