package customerServiceTest;

import com.albaneroassignment.customerservice.entity.Customer;
import com.albaneroassignment.customerservice.exception.ServiceException;
import com.albaneroassignment.customerservice.repository.CustomerRepository;
import com.albaneroassignment.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
    }

    @Test
    void getCustomerById() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
    }

    @Test
    void createCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
    }

    @Test
    void updateCustomer() {
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assertNotNull(result);
    }

    @Test
    void deleteCustomer() {
        Long customerId = 1L;

        boolean result = customerService.deleteCustomer(customerId);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void bulkLoadCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.saveAll(customers)).thenReturn(customers);

        List<Customer> result = customerService.bulkLoadCustomers(customers);

        assertEquals(2, result.size());
    }

}
