module app.lab7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.lab7 to javafx.fxml;
    exports app.lab7;
}