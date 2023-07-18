module com.example.compsciia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    opens com.example.compsciia to javafx.fxml;
    exports com.example.compsciia;
}