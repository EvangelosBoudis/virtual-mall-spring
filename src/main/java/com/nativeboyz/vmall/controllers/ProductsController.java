package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.exceptions.FilePriorityNotFound;
import com.nativeboyz.vmall.models.ActionType;
import com.nativeboyz.vmall.models.OrderedActionImage;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.criteria.QueryCriteria;
import com.nativeboyz.vmall.models.dto.*;
import com.nativeboyz.vmall.services.products.ProductsService;
import com.nativeboyz.vmall.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;
    private final StorageService storageService;

    @Autowired
    public ProductsController(ProductsService productsService, StorageService storageService) {
        this.productsService = productsService;
        this.storageService = storageService;
    }

    @GetMapping()
    public Page<ProductDto> getProducts(QueryCriteria criteria) {
        // TODO: JWT customerId
        return productsService.findProducts(null, criteria);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(
            @PathVariable UUID id,
            @RequestParam("requesterId") UUID requesterId // TODO: JWT
    ) {
        return productsService.findProduct(id, requesterId);
    }

    @GetMapping("/{id}/details")
    public ProductDetailsDto getProductDetails(
            @PathVariable UUID id,
            @RequestParam("requesterId") UUID requesterId // TODO: JWT
    ) {
        return productsService.findProductDetails(id, requesterId);
    }

    @PostMapping()
    public ProductDetailsDto createProduct(
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("product") @Valid ProductCriteria criteria
    ) {

        List<OrderedImage> savedOrderedImages = saveOrderedImages(files, criteria.getOrderedImages());
        criteria.setOrderedImages(savedOrderedImages);

        return productsService.saveProduct(criteria);
    }

    @PutMapping("/{id}")
    public ProductDetailsDto updateProduct(
            @PathVariable UUID id,
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("product") @Valid ProductCriteria criteria
    ) {

        List<OrderedActionImage> orderedActionImages = OrderedActionImage.merge(
                criteria.getOrderedImages(),
                productsService.findProductImageNames(id)
        );

        deleteOrderedImages(OrderedActionImage.filter(orderedActionImages, ActionType.DELETE));

        List<OrderedImage> savedOrderedImages = saveOrderedImages(
                files,
                OrderedActionImage.filter(orderedActionImages, ActionType.SAVE)
        );

        criteria.setOrderedImages(new ArrayList<>() {{
            addAll(OrderedActionImage.filter(orderedActionImages, ActionType.UPDATE));
            addAll(savedOrderedImages);
        }});

        return productsService.updateProduct(id, criteria);
    }

    @DeleteMapping("/{id}")
    public TransactionDto deleteProduct(@PathVariable UUID id) {

        storageService.deleteIfExists(productsService.findProductImageNames(id));
        productsService.deleteProduct(id);
        return new TransactionDto("Product: " + id.toString() + " deleted successfully");
    }

    private <T extends OrderedImage> List<T> saveOrderedImages(
            List<MultipartFile> files,
            List<T> orderedImages
    ) {

        // save validation
        if (files.size() != orderedImages.size()) {
            throw new FilePriorityNotFound();
        }

        // save process
        List<String> savedFiles = storageService.save(files).collect(Collectors.toList());

        // replace file names
        for (int i = 0; i < savedFiles.size(); i++) {
            orderedImages.get(i).setName(savedFiles.get(i));
        }

        return orderedImages;
    }

    private <T extends OrderedImage> void deleteOrderedImages(List<T> orderedImages) {

        List<String> imageNames = orderedImages
                .stream()
                .map(T::getName)
                .collect(Collectors.toList());

        storageService.deleteIfExists(imageNames);
    }

}
