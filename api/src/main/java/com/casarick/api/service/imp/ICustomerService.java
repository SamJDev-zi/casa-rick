package com.casarick.api.service.imp;

import com.casarick.api.dto.CustomerDTO;
import com.casarick.api.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<CustomerDTO> getCustomers();
    CustomerDTO getCustomerBtId(Long id);
    CustomerDTO createCustomer(Customer customer);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
}
