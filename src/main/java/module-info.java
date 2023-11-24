module com.cultura {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive java.sql;

    opens com.cultura to javafx.fxml;
    exports com.cultura;

}
