package com.order.service;

import com.order.client.CustomerClient;
import com.order.exception.CustomerNotFoundException;
import com.order.model.Customer;
import com.order.model.CustomerResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerClient customerClient;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @CircuitBreaker(name="customerServiceCB", fallbackMethod="fallbackGetCustomer")
    public CustomerResponse getCustomer(Long id) {
        CustomerResponse customerResponse=new CustomerResponse();
        Customer customer=customerClient.getCustomerById(id);
        if(customer!=null) {
            customerResponse.setCustomer(customer);
            customerResponse.setIsError(false);
        }
        return  customerResponse;
    }

    public CustomerResponse fallbackGetCustomer(Long id, Throwable t) {
        CustomerResponse customerResponse=new CustomerResponse();
        if(t instanceof CustomerNotFoundException) {
            customerResponse.setIsError(true);
            customerResponse.setErrorMessage(t.getMessage());
        }else {
            customerResponse.setIsError(true);
            customerResponse.setErrorMessage("Customer Service Temporarily Unavailable. Please try later.");
        }

        return customerResponse;
    }

}