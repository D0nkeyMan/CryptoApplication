package edu.miracosta.cs112.cryptographyapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AesCipherSceneController implements Initializable {
    @FXML
    private ChoiceBox<String> inputModeBox;

    @FXML
    private ChoiceBox<String> outputModeBox;

    @FXML
    private TextField keyTextField;

    @FXML
    private TextField ivTextField;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Label messageLabel;

    private final String[] modes = {"Plaintext", "Base64", "Hex"};

    // Convert the String modes to corresponding integers
    private int stringToMode(String mode) {
        mode = mode.toLowerCase();
        return switch (mode) {
            case "plaintext" -> 0;
            case "base64" -> 1;
            case "hex" -> 2;
            default -> -1; // Invalid
        };
    }

    // Create an Aes object using inputted data and update the message label according to any exceptions.
    private Aes createAesObject() {
        int inputMode = stringToMode(inputModeBox.getValue());
        int outputMode = stringToMode(outputModeBox.getValue());
        String key = keyTextField.getText();
        String iv = ivTextField.getText();

        try {
            return new Aes(inputMode, outputMode, key.getBytes(), iv.getBytes());
        } catch (IllegalArgumentException | InvalidKeyLengthException e) {
            messageLabel.setText(e.getMessage());
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the choice box with modes
        inputModeBox.getItems().addAll(modes);
        outputModeBox.getItems().addAll(modes);

        // Set the default modes to Plaintext
        inputModeBox.setValue("Plaintext");
        outputModeBox.setValue("Plaintext");
    }

    @FXML
    protected void onEncryptButtonClick() {
        Aes aes = createAesObject();
        if (aes != null) {
            try {
                outputTextArea.setText(aes.encrypt(inputTextArea.getText()));
                messageLabel.setText("Encryption successful!");
            } catch (RuntimeException e) {
                messageLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    protected void onDecryptButtonClick() {
        Aes aes = createAesObject();
        if (aes != null) {
            try {
                outputTextArea.setText(aes.decrypt(inputTextArea.getText()));
                messageLabel.setText("Decryption successful!");
            } catch (RuntimeException e) {
                messageLabel.setText(e.getMessage());
            }
        }
    }

    // Load the start menu
    @FXML
    protected void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        Parent startView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartScene.fxml")));
        Scene startScene = new Scene(startView);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(startScene);
        window.show();
    }
}