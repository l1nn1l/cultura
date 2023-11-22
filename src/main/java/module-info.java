module com.cultura {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cultura to javafx.fxml;
    exports com.cultura.objects;
    exports com.cultura;
}
