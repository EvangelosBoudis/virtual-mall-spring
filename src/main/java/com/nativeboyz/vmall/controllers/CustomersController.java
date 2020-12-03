package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.criteria.CustomerCriteria;
import com.nativeboyz.vmall.models.criteria.PageCriteria;
import com.nativeboyz.vmall.models.dto.TransactionDto;
import com.nativeboyz.vmall.services.customers.CustomersService;
import com.nativeboyz.vmall.services.storage.StorageService;
import com.nativeboyz.vmall.tools.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController()
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;
    private final StorageService storageService;

    @Autowired
    public CustomersController(CustomersService customersService, StorageService storageService) {
        this.customersService = customersService;
        this.storageService = storageService;
    }

    @GetMapping()
    public Page<CustomerEntity> getCustomers(PageCriteria criteria) {
        return customersService.findCustomers(criteria.asPageable())
                .map(customer -> {
                    String url = UrlGenerator.fileNameToUrl(customer.getImageName());
                    customer.setImageName(url);
                    return customer;
                });
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable UUID id) {
        CustomerEntity customer = customersService.findCustomer(id);
        customer.setImageName(UrlGenerator.fileNameToUrl(customer.getImageName()));
        return customer;
    }

    @DeleteMapping("/{id}")
    public TransactionDto deleteCustomer(@PathVariable UUID id) {
        CustomerEntity customer = customersService.findCustomer(id);
        String imageName = customer.getImageName();
        if (imageName != null) storageService.deleteIfExists(imageName);
        customersService.deleteCustomer(id);
        return new TransactionDto("Customer: " + id.toString() + " deleted successfully");
    }

    @PostMapping()
    public CustomerEntity createCustomer(@Valid CustomerCriteria criteria) {
        String filename = (criteria.getFile() != null) ? storageService.save(criteria.getFile()) : null;
        CustomerEntity customer = customersService.saveCustomer(
                new CustomerEntity(
                        criteria.getFirstname(),
                        criteria.getLastname(),
                        criteria.getEmail(),
                        filename,
                        criteria.getPhoneNumber()
                )
        );
        String url = UrlGenerator.fileNameToUrl(customer.getImageName());
        customer.setImageName(url);
        return customer;
    }

    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(
            @PathVariable UUID id,
            @Valid CustomerCriteria criteria
    ) {
        CustomerEntity customer = customersService.findCustomer(id);
        String updatedImageName = storageService.replaceIfExists(customer.getImageName(), criteria.getFile());
        customer.update(
                criteria.getFirstname(),
                criteria.getLastname(),
                criteria.getEmail(),
                criteria.getPhoneNumber(),
                updatedImageName
        );
        CustomerEntity updatedCustomer = customersService.saveCustomer(customer);
        String url = UrlGenerator.fileNameToUrl(updatedCustomer.getImageName());
        updatedCustomer.setImageName(url);
        return updatedCustomer;
    }

    @GetMapping("/{customerId}/products")
    public Page<ProductEntity> getCustomerProducts(
            @PathVariable UUID customerId,
            PageCriteria criteria
    ) {
        return customersService.findCustomerProducts(customerId, criteria.asPageable());
    }

/*    @GetMapping("/{customerId}/favorites")
    public Page<Product> getCustomerFavoriteProducts(
            @PathVariable UUID customerId
    ) {

    }*/

}
// @RequestParam(value = "file", required = false) MultipartFile file,
