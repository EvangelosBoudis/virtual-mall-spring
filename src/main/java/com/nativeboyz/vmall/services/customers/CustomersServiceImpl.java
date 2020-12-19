package com.nativeboyz.vmall.services.customers;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersServiceImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public CustomerEntity findCustomer(UUID customerId) {
        return customersRepository.findById(customerId).orElseThrow();
    }

    @Override
    public Page<CustomerEntity> findCustomers(Pageable pageable) {
        return customersRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CustomerEntity saveCustomer(CustomerEntity entity) {
        return customersRepository.save(entity);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customersRepository.deleteById(customerId);
    }

}
