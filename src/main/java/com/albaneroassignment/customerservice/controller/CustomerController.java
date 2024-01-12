package com.albaneroassignment.customerservice.controller;

import com.albaneroassignment.customerservice.entity.Customer;
import com.albaneroassignment.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "Customer microservice in the world of microservices")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("getAllCustomer")
    @Operation(
            summary = "Fetch all customers",
            description = "fetches all customer entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all customers", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    @Operation(
            summary = "Fetch customer by ID",
            description = "fetches a customer entity by its ID from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        try {
            Optional<Customer> customer = customerService.getCustomerById(customerId);
            return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error fetching customer by ID: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createCustomer")
    @Operation(
            summary = "Create a new customer",
            description = "creates a new customer entity in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "customer created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating customer", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{customerId}")
    @Operation(
            summary = "Update customer by ID",
            description = "updates a customer entity by its ID in the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer updated successfully"),
            @ApiResponse(responseCode = "404", description = "customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        try {
            Optional<Customer> customer = Optional.ofNullable(customerService.updateCustomer(customerId, updatedCustomer));
            return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating customer with ID: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}")
    @Operation(
            summary = "Delete customer by ID",
            description = "deletes a customer entity by its ID from the data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        try {
            boolean deleted = customerService.deleteCustomer(customerId);
            return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting customer with ID: {}", customerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulkLoad")
    @Operation(
            summary = "Bulk load customers",
            description = "creates multiple customer entities in the data source in bulk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "bulk load successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Customer>> bulkLoadCustomers(@RequestBody List<Customer> customers) {
        try {
            List<Customer> savedCustomers = customerService.bulkLoadCustomers(customers);
            return new ResponseEntity<>(savedCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bulkUpdate")
    @Operation(
            summary = "Bulk update customers",
            description = "updates multiple customer entities in the data source in bulk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "bulk update successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Customer>> bulkUpdateCustomers(@RequestBody List<Customer> updatedCustomers) {
        try {
            List<Customer> updatedCustomersList = customerService.bulkUpdateCustomers(updatedCustomers);
            return new ResponseEntity<>(updatedCustomersList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
