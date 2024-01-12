package com.albaneroassignment.customerservice.customerControllerTest;

import com.albaneroassignment.customerservice.controller.CustomerController;
import com.albaneroassignment.customerservice.entity.Customer;
import com.albaneroassignment.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCustomers_Success() {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(new Customer(), new Customer()));

        ResponseEntity<List<Customer>> result = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
    }

    @Test
    void getCustomerById_Success() {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(new Customer()));

        ResponseEntity<Customer> result = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void createCustomer_Success() {
        Customer inputCustomer = new Customer();
        when(customerService.createCustomer(inputCustomer)).thenReturn(new Customer());

        ResponseEntity<Customer> result = customerController.createCustomer(inputCustomer);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void updateCustomer_Success() {
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        when(customerService.updateCustomer(customerId, updatedCustomer)).thenReturn(new Customer());

        ResponseEntity<Customer> result = customerController.updateCustomer(customerId, updatedCustomer);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void deleteCustomer_Success() {
        Long customerId = 1L;
        when(customerService.deleteCustomer(customerId)).thenReturn(true);

        ResponseEntity<Void> result = customerController.deleteCustomer(customerId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void bulkLoadCustomers_Success() {
        List<Customer> inputCustomers = Arrays.asList(new Customer(), new Customer());
        when(customerService.bulkLoadCustomers(inputCustomers)).thenReturn(Arrays.asList(new Customer(), new Customer()));

        ResponseEntity<List<Customer>> result = customerController.bulkLoadCustomers(inputCustomers);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
    }

    @Test
    void bulkUpdateCustomers_Success() {
        List<Customer> updatedCustomers = Arrays.asList(new Customer(), new Customer());
        when(customerService.bulkUpdateCustomers(updatedCustomers)).thenReturn(Arrays.asList(new Customer(), new Customer()));

        ResponseEntity<List<Customer>> result = customerController.bulkUpdateCustomers(updatedCustomers);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
    }
}
