package com.nativeboyz.vmall.services.customers;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomersService {

    CustomerEntity findCustomer(UUID id);

    Page<CustomerEntity> findCustomers(Pageable pageable);

    CustomerEntity saveCustomer(CustomerEntity entity);

    void deleteCustomer(UUID id);

    Page<ProductEntity> findCustomerProducts(UUID id, Pageable pageable);

}
