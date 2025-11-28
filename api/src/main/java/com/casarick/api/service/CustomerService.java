package com.casarick.api.service;

import com.casarick.api.dto.CustomerDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Customer;
import com.casarick.api.reposiroty.CustomerRepository;
import com.casarick.api.service.imp.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<CustomerDTO> getCustomers() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public CustomerDTO getCustomerBtId(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " +id));

        return Mapper.toDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(Customer customer) {
        return Mapper.toDTO(repository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        customer.setName(customerDTO.getName());
        customer.setLastName(customer.getLastName());
        customer.setPhoneNumber(customer.getPhoneNumber());

        return Mapper.toDTO(repository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));

        repository.delete(customer);
    }
}
