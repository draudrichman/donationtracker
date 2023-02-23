module com.start {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.start to javafx.fxml;
    exports com.start;
}