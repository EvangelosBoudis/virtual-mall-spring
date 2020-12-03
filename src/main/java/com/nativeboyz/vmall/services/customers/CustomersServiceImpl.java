package com.nativeboyz.vmall.services.customers;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.repositories.customers.CustomersRepository;
import com.nativeboyz.vmall.repositories.products.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomersRepository customersRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public CustomersServiceImpl(
            CustomersRepository customersRepository,
            ProductsRepository productsRepository
    ) {
        this.customersRepository = customersRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public CustomerEntity findCustomer(UUID id) {
        return customersRepository.findById(id).orElseThrow();
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
    public void deleteCustomer(UUID id) {
        customersRepository.deleteById(id);
    }

    @Override
    public Page<ProductEntity> findCustomerProducts(UUID id, Pageable pageable) {
        return productsRepository.findByCustomerId(id, pageable);
    }

}
