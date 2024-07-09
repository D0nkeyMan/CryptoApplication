module edu.miracosta.cs112.cryptographyapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.miracosta.cs112.cryptographyapplication to javafx.fxml;
    exports edu.miracosta.cs112.cryptographyapplication;
}