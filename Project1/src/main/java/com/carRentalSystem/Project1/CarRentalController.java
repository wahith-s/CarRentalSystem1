package com.carRentalSystem.Project1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("cars", carRentalService.getAvailableCars());
        return "index";
    }

    @PostMapping("/rent")
    public String rentCar(
            @RequestParam String customerName,
            @RequestParam Long carId,
            @RequestParam int days,
            Model model) {

        // Find or create the customer
        Customer customer = customerRepository.findByName(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setName(customerName);
            customer.setTotalCost(0.0); // Initialize total cost
            customerRepository.save(customer);
        }

        double cost = carRentalService.calculateCost(carId, days);
        carRentalService.rentCar(carId, customer.getId(), days);

        model.addAttribute("customerName", customer.getName());
        model.addAttribute("cost", cost);

        return "confirmation";
    }

    @PostMapping("/return")
    public String returnCar(@RequestParam Long carId) {
        carRentalService.returnCar(carId);
        return "redirect:/";
    }
}
