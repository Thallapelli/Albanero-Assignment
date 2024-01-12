package com.albaneroassignment.customerservice.service;

import com.albaneroassignment.customerservice.entity.Customer;
import com.albaneroassignment.customerservice.exception.ServiceException;
import com.albaneroassignment.customerservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all customers", e);
            throw new ServiceException("Error occurred while retrieving all customers", e);
        }
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        try {
            return customerRepository.findById(customerId);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving customer with ID: {}", customerId, e);
            throw new ServiceException("Error occurred while retrieving customer with ID: " + customerId, e);
        }
    }

    public Customer createCustomer(Customer customer) {
        try {
            // Additional validation and business logic can be added here
            return customerRepository.save(customer);
        } catch (Exception e) {
            logger.error("Error occurred while creating a new customer", e);
            throw new ServiceException("Error occurred while creating a new customer", e);
        }
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        try {
            // Additional validation and business logic can be added here
            Optional<Customer> existingCustomer = customerRepository.findById(customerId);
            if (existingCustomer.isPresent()) {
                updatedCustomer.setCustomerId(customerId);
                return customerRepository.save(updatedCustomer);
            }
            return null; // Handle not found scenario
        } catch (Exception e) {
            logger.error("Error occurred while updating customer with ID: {}", customerId, e);
            throw new ServiceException("Error occurred while updating customer with ID: " + customerId, e);
        }
    }

    public boolean deleteCustomer(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while deleting customer with ID: {}", customerId, e);
            throw new ServiceException("Error occurred while deleting customer with ID: " + customerId, e);
        }
    }

    public List<Customer> bulkLoadCustomers(List<Customer> customers) {
        try {
            // Additional validation and business logic for bulk load
            return customerRepository.saveAll(customers);
        } catch (Exception e) {
            logger.error("Error occurred during bulk load of customers", e);
            throw new ServiceException("Error occurred during bulk load of customers", e);
        }
    }

    public List<Customer> bulkUpdateCustomers(List<Customer> updatedCustomers) {
        try {
            // Additional validation and business logic for bulk update
            List<Customer> existingCustomers = customerRepository.findAllById(
                    updatedCustomers.stream().map(Customer::getCustomerId).collect(Collectors.toList())
            );

            // Update existing customers with the changes
            for (Customer updatedCustomer : updatedCustomers) {
                Optional<Customer> existingCustomer = existingCustomers.stream()
                        .filter(c -> c.getCustomerId().equals(updatedCustomer.getCustomerId()))
                        .findFirst();

                existingCustomer.ifPresent(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhone(updatedCustomer.getPhone());
                    customer.setAddress(updatedCustomer.getAddress());
                    customer.setCompanyName(updatedCustomer.getCompanyName());
                    customer.setIndustryType(updatedCustomer.getIndustryType());
                    customer.setCustomerStatus(updatedCustomer.getCustomerStatus());
                    customer.setAccountManager(updatedCustomer.getAccountManager());
                    customer.setAudit(updatedCustomer.getAudit());
                    customerRepository.save(customer);
                });
            }

            return updatedCustomers;
        } catch (Exception e) {
            logger.error("Error occurred during bulk update of customers", e);
            throw new ServiceException("Error occurred during bulk update of customers", e);
        }
    }
}
