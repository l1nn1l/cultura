module com.cultura {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.cultura to javafx.fxml;
    exports com.cultura;

}
