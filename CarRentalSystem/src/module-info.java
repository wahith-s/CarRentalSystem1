module CarRentalSystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.carrental.controller to javafx.fxml;
    exports com.carrental;
    exports com.carrental.controller;
}
