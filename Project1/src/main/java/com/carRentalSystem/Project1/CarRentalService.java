package com.carRentalSystem.Project1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Car> getAvailableCars() {
        return carRepository.findByIsAvailableTrue();
    }

    public double calculateCost(Long carId, int days) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        return car.getBasePricePerDay() * days;
    }

    public void rentCar(Long carId, Long customerId, int days) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        car.setAvailable(false);
        carRepository.save(car);
        customer.setTotalCost(customer.getTotalCost() + calculateCost(carId, days));
        customerRepository.save(customer);
    }

    public void returnCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setAvailable(true);
        carRepository.save(car);
    }
}
