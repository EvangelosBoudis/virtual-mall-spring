package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.criteria.product.ProductTransformedCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.entities.ProductImageEntity;
import com.nativeboyz.vmall.models.criteria.PageCriteria;
import com.nativeboyz.vmall.models.criteria.product.ProductFileCriteria;
import com.nativeboyz.vmall.models.dto.TransactionDto;
import com.nativeboyz.vmall.services.products.ProductsService;
import com.nativeboyz.vmall.services.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProductsService productsService;
    private final StorageService storageService;

    @Autowired
    public ProductsController(ProductsService productsService, StorageService storageService) {
        this.productsService = productsService;
        this.storageService = storageService;
    }

    @GetMapping()
    public Page<ProductEntity> getProducts(PageCriteria criteria) {
        //logger.info("pre service");
        return productsService.findProducts(criteria.asPageable());
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(
            @PathVariable UUID id,
            @RequestParam("customerId") UUID customerId // TODO: Replace with JWT
    ) {
        return productsService.findProduct(id, customerId);
    }

    @DeleteMapping("/{id}")
    public TransactionDto deleteProduct(@PathVariable UUID id) {
        ProductEntity productEntity = productsService.findProduct(id);
        List<String> imageNames = productEntity
                .getProductImageEntities()
                .stream()
                .map(ProductImageEntity::getImageName)
                .collect(Collectors.toList());
        storageService.deleteIfExists(imageNames);
        productsService.deleteProduct(id);
        return new TransactionDto("Product: " + id.toString() + " deleted successfully");
    }

    @PostMapping()
    public ProductEntity createProduct(@Valid ProductFileCriteria criteria) {
        String[] imagesNames = storageService
                .save(Arrays.asList(criteria.getFiles()))
                .toArray(String[]::new);

        return productsService.saveProduct(new ProductTransformedCriteria(criteria, imagesNames));
    }

}
