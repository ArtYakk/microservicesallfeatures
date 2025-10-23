package com.customer.service;

import com.customer.entity.Customer;
import com.customer.exception.CustomerAlreadyExistsException;
import com.customer.exception.CustomerNotFoundException;
import com.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        Customer customer = customerRepository.getCustomerByEmail(newCustomer.getEmail());
        if(customer != null){
            throw new CustomerAlreadyExistsException(newCustomer.getEmail());
        }
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(Customer newCustomer, Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = null;
        if(customerOptional.isPresent()){
            customer = customerOptional.get();
            customer.setName(newCustomer.getName());
            customer.setEmail(newCustomer.getEmail());
            customer.setLocation(newCustomer.getLocation());
        }
        return customerRepository.save(customer);
    }

    @Override
    public String deleteCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = null;
        String deleteMessage = null;
        if(customerOptional.isPresent()){
            customer = customerOptional.get();
            customerRepository.delete(customer);
            deleteMessage = String.format("Customer successfully deleted for id: %d", id);
        }
        return deleteMessage;
    }
}
