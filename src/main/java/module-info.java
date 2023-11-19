module com.cultura {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.cultura to javafx.fxml;
    exports com.cultura;

}
