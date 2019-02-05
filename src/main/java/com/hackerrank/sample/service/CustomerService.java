package com.hackerrank.sample.service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Customer;
import com.hackerrank.sample.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Integer custId) {
        System.out.println("Reached get customer.");
        Optional<Customer> result = customerRepository.findById(custId);
        return result.isPresent() ? result.get() : null;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer addCustomer(Customer customer) {
        System.out.println("Reached addCustomer with:" + customer);
        try{
            if (getCustomer(customer.getCustomerId()) == null){
                System.out.println("Adding new customer:" + customer);
                return customerRepository.save(customer);
            }else {
                System.out.println("Customer not found.");
                throw new BadResourceRequestException("Customer with same id:" + customer.getCustomerId() + " exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public Customer updateCustomer(Integer custId, Customer customer) {

        try{
            System.out.println("Reached updateCustomer");
            if (getCustomer(custId) != null){
                return customerRepository.save(customer);
            }else {
                throw new BadResourceRequestException("Customer with id:" + customer.getCustomerId() + " doesn't exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public void deleteCustomer(Integer custID) {
        customerRepository.deleteById(custID);
    }

    public void deleteCustomers() {
        customerRepository.deleteAllInBatch();
    }
}
