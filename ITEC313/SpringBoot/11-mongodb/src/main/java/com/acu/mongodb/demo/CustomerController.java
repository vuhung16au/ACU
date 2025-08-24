package com.acu.mongodb.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    // Show list of all customers
    @GetMapping
    public String listCustomers(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    // Show form to add new customer
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/add";
    }

    // Process add customer form
    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    // Show form to edit customer
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customers/edit";
    }

    // Process edit customer form
    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable String id, @ModelAttribute Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    // Show delete confirmation form
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable String id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "customers/delete";
    }

    // Process delete customer
    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    // Show search form
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchResults", null);
        return "customers/search";
    }

    // Process search
    @PostMapping("/search")
    public String searchCustomers(@RequestParam String searchType, 
                                 @RequestParam String searchValue, 
                                 Model model) {
        List<Customer> searchResults = null;
        
        switch (searchType) {
            case "firstName":
                searchResults = customerRepository.findByFirstName(searchValue);
                break;
            case "lastName":
                searchResults = customerRepository.findByLastName(searchValue);
                break;
            case "city":
                searchResults = customerRepository.findByCity(searchValue);
                break;
            case "country":
                searchResults = customerRepository.findByCountry(searchValue);
                break;
        }
        
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        return "customers/search";
    }

    // Home page redirect
    @GetMapping("/")
    public String home() {
        return "redirect:/customers";
    }
}
