package com.carrental.controller;

import com.carrental.model.Car;
import com.carrental.model.CarRentalSystem;
import com.carrental.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewController {

    @FXML
    private TextField customerNameField;

    @FXML
    private ComboBox<Car> carComboBox;

    @FXML
    private TextField rentalDaysField;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Label statusLabel;

    private CarRentalSystem carRentalSystem;

    // Map to store which customer rented which car
    private Map<String, String> carCustomerMap = new HashMap<>();

    public void setCarRentalSystem(@SuppressWarnings("exports") CarRentalSystem carRentalSystem) {
        this.carRentalSystem = carRentalSystem;
        loadAvailableCars();
    }

    private void loadAvailableCars() {
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        carComboBox.getItems().setAll(availableCars);
    }

    @FXML
    private void handleRentCar(ActionEvent event) {
        String customerName = customerNameField.getText();
        Car selectedCar = carComboBox.getValue();
        int rentalDays = Integer.parseInt(rentalDaysField.getText());

        if (customerName.isEmpty() || selectedCar == null || rentalDays <= 0) {
            statusLabel.setText("Invalid input");
            return;
        }

        Customer customer = new Customer("C" + System.currentTimeMillis(), customerName);
        carRentalSystem.addCustomer(customer);
        carRentalSystem.rentCar(selectedCar, customer, rentalDays);

        double totalCost = selectedCar.calculatePrice(rentalDays);
        totalCostLabel.setText(String.format("₹%.2f", totalCost));

        // Store the customer name associated with the car ID
        carCustomerMap.put(selectedCar.getCarId(), customerName);

        statusLabel.setText(customerName + " rented the car successfully");
        loadAvailableCars();
    }

    @FXML
    private void handleReturnCar(ActionEvent event) {
        Car selectedCar = carComboBox.getValue();
        if (selectedCar != null) {
            String customerName = carCustomerMap.get(selectedCar.getCarId());
            carRentalSystem.returnCar(selectedCar);
            carCustomerMap.remove(selectedCar.getCarId());
            statusLabel.setText(customerName + " returned the car");
            loadAvailableCars();
        } else {
            statusLabel.setText("Select a car to return");
        }
    }
}
