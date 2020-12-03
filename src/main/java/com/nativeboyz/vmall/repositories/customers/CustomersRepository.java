package com.nativeboyz.vmall.repositories.customers;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomersRepository extends JpaRepository<CustomerEntity, UUID> {

}
