package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.exceptions.FilePriorityNotFound;
import com.nativeboyz.vmall.models.ActionType;
import com.nativeboyz.vmall.models.ImageAction;
import com.nativeboyz.vmall.models.criteria.ProductCriteria;
import com.nativeboyz.vmall.models.dto.*;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import com.nativeboyz.vmall.models.criteria.PageCriteria;
import com.nativeboyz.vmall.services.products.ProductsService;
import com.nativeboyz.vmall.services.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    public Page<ProductDto> getProducts(PageCriteria criteria) {
        return productsService.findProducts(criteria.asPageable(), null);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(
            @PathVariable UUID id,
            @RequestParam("customerId") UUID customerId
    ) {
        // TODO: Replace RequestParam with JWT
        return productsService.findProduct(id, customerId);
    }

    @GetMapping("/{id}/info")
    public ProductInfoDto getProductInfo(
            @PathVariable UUID id,
            @RequestParam("customerId") UUID customerId
    ) {
        // TODO: Replace RequestParam with JWT
        return productsService.findProductInfo(id, customerId);
    }

    @PostMapping()
    public ProductEntity createProduct(
            @RequestPart("filesPayload") List<MultipartFile> files,
            @RequestPart("jsonPayload") @Valid ProductCriteria criteria
    ) {

        List<ImageAction> transformed = saveFilesProcess(
                files,
                ImageAction.filter(criteria.getImageActions(), ActionType.SAVE)
        );
        criteria.setImageActions(transformed);
        return productsService.saveProduct(criteria);
    }

    @PutMapping("/{id}")
    public ProductInfoDto updateProduct(
            @PathVariable UUID id,
            @RequestPart("filesPayload") List<MultipartFile> files,
            @RequestPart("jsonPayload") @Valid ProductCriteria criteria
    ) {

        List<ImageAction> transformed = filesProcess(
                files,
                productsService.findProductImageNames(id),
                criteria.getImageActions()
        );
        criteria.setImageActions(transformed);

        return productsService.updateProduct(id, criteria);
    }

    @DeleteMapping("/{id}")
    public TransactionDto deleteProduct(@PathVariable UUID id) {
        storageService.deleteIfExists(productsService.findProductImageNames(id));
        productsService.deleteProduct(id);
        return new TransactionDto("Product: " + id.toString() + " deleted successfully");
    }

    private List<ImageAction> filesProcess(
            List<MultipartFile> files,
            List<String> existingImageNames,
            List<ImageAction> imageActions
    ) {

        List<ImageAction> imagesForDelete = ImageAction.filter(imageActions, ActionType.DELETE);
        List<ImageAction> imagesForSave = ImageAction.filter(imageActions, ActionType.SAVE);
        List<ImageAction> imagesForUpdate = ImageAction.filter(imageActions, ActionType.UPDATE);

        deleteFilesProcess(existingImageNames, imagesForDelete);

        List<ImageAction> union = saveFilesProcess(files, imagesForSave);
        union.addAll(imagesForUpdate);

        return union;
    }

    private List<ImageAction> saveFilesProcess(
            List<MultipartFile> files,
            List<ImageAction> imagesForSave
    ) {
        // save validation
        if (files.size() != imagesForSave.size()) {
            throw new FilePriorityNotFound();
        }

        // save process
        List<String> savedFiles = storageService.save(files).collect(Collectors.toList());

        // replace file names
        List<ImageAction> transformed = new ArrayList<>();

        for (int i = 0; i < savedFiles.size(); i++) {
            transformed.add(new ImageAction(
                    savedFiles.get(i),
                    imagesForSave.get(i).getPriorityLevel(),
                    imagesForSave.get(i).getActionType()
            ));
        }

        return transformed;
    }

    private void deleteFilesProcess(
            List<String> existingImageNames,
            List<ImageAction> imagesForDelete
    ) {
        // delete validation
        for (ImageAction img : imagesForDelete) {
            if (!existingImageNames.contains(img.getName())) {
                throw new NoSuchElementException("The provided images for update does not exist");
            }
        }

        // delete process
        List<String> imageNamesForDelete = imagesForDelete
                .stream()
                .map(ImageAction::getName)
                .collect(Collectors.toList());

        storageService.deleteIfExists(imageNamesForDelete);
    }

}
