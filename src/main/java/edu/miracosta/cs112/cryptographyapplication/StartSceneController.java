package edu.miracosta.cs112.cryptographyapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartSceneController {
    // Load the caesar cipher menu
    @FXML
    protected void onCaesarCipherButtonClick(ActionEvent actionEvent) throws IOException {
        Parent caesarCipherView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CaesarCipherScene.fxml")));
        Scene caesarCipherScene = new Scene(caesarCipherView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(caesarCipherScene);
        window.show();
    }

    // Load the vigenere cipher menu
    @FXML
    protected void onVigenereCipherButtonClick(ActionEvent actionEvent) throws IOException {
        Parent caesarCipherView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("VigenereCipherScene.fxml")));
        Scene caesarCipherScene = new Scene(caesarCipherView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(caesarCipherScene);
        window.show();
    }

    // Load the aes cipher menu
    @FXML
    protected void onAesCipherButtonClick(ActionEvent actionEvent) throws IOException {
        Parent caesarCipherView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AesCipherScene.fxml")));
        Scene caesarCipherScene = new Scene(caesarCipherView);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(caesarCipherScene);
        window.show();
    }
}