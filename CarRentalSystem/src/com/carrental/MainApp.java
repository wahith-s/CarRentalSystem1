package com.carrental;

import com.carrental.controller.MainViewController;
import com.carrental.model.Car;
import com.carrental.model.CarRentalSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @SuppressWarnings("exports")
	@Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carrental/view/MainView.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Car Rental System");
            primaryStage.show();

            MainViewController controller = loader.getController();
            CarRentalSystem carRentalSystem = new CarRentalSystem();
            carRentalSystem.addCar(new Car("C001", "Toyota", "Camry", 5000));
            carRentalSystem.addCar(new Car("C002", "Honda", "Accord", 6000));
            carRentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 11000));
            controller.setCarRentalSystem(carRentalSystem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
