package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.entities.CustomerEntity;
import com.nativeboyz.vmall.models.criteria.CustomerCriteria;
import com.nativeboyz.vmall.models.criteria.PageCriteria;
import com.nativeboyz.vmall.models.dto.TransactionDto;
import com.nativeboyz.vmall.services.customers.CustomersService;
import com.nativeboyz.vmall.services.products.ProductsService;
import com.nativeboyz.vmall.services.storage.StorageService;
import com.nativeboyz.vmall.tools.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController()
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;
    private final ProductsService productsService;
    private final StorageService storageService;

    @Autowired
    public CustomersController(CustomersService customersService, ProductsService productsService, StorageService storageService) {
        this.customersService = customersService;
        this.productsService = productsService;
        this.storageService = storageService;
    }

    @GetMapping()
    public Page<CustomerEntity> getCustomers(PageCriteria criteria) {
        Page<CustomerEntity> customers = customersService.findCustomers(criteria.getPageable());
        customers.forEach(customer -> {
            String url = UrlGenerator.fileNameToUrl(customer.getImageName());
            customer.setImageName(url);
        });
        return customers;
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable UUID id) {
        CustomerEntity customer = customersService.findCustomer(id);
        customer.setImageName(UrlGenerator.fileNameToUrl(customer.getImageName()));
        return customer;
    }

    @GetMapping("/{id}/products")
    public Page<ProductDto> getCustomerProducts(
            @PathVariable UUID id,
            QueryCriteria criteria
    ) {
        return productsService.findCustomerProducts(id, criteria);
    }

    @GetMapping("/{id}/favorite-products")
    public Page<ProductDto> getCustomerFavoriteProducts(
            @PathVariable UUID id,
            QueryCriteria criteria
    ) {
        return productsService.findCustomerFavoriteProducts(id, criteria);
    }

    @GetMapping("/{id}/viewed-products")
    public Page<ProductDto> getCustomerViewedProducts(
            @PathVariable UUID id,
            QueryCriteria criteria
    ) {
        return productsService.findCustomerViewedProducts(id, criteria);
    }

    @GetMapping("/{id}/search-history")
    public Page<String> getCustomerSearchHistory(
            @PathVariable UUID id,
            PageCriteria criteria
    ) {
        // TODO: implement
        return null;
    }

    @PostMapping()
    public CustomerEntity createCustomer(
            @RequestPart("file") MultipartFile file,
            @RequestPart("customer") @Valid CustomerCriteria criteria
    ) {
        String filename = (file != null) ? storageService.save(file) : null;
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
            @RequestPart("file") @NotNull MultipartFile file,
            @RequestPart("customer") @Valid CustomerCriteria criteria
    ) {
        CustomerEntity customer = customersService.findCustomer(id);
        String updatedImageName = storageService.replaceIfExists(customer.getImageName(), file);
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

    @DeleteMapping("/{id}")
    public TransactionDto deleteCustomer(@PathVariable UUID id) {
        CustomerEntity customer = customersService.findCustomer(id);
        String imageName = customer.getImageName();
        if (imageName != null) storageService.deleteIfExists(imageName);
        customersService.deleteCustomer(id);
        return new TransactionDto("Customer: " + id.toString() + " deleted successfully");
    }

}

// @RequestParam(value = "file", required = false) MultipartFile file,
