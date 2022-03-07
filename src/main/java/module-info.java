module com.example.echecfxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;


    opens com.example.echecfxml to javafx.fxml;
    exports com.example.echecfxml;
}