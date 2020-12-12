package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.criteria.CategoryCriteria;
import com.nativeboyz.vmall.models.criteria.PageCriteria;
import com.nativeboyz.vmall.models.dto.TransactionDto;
import com.nativeboyz.vmall.services.categories.CategoriesService;
import com.nativeboyz.vmall.services.storage.StorageService;
import com.nativeboyz.vmall.tools.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@PropertySource("classpath:messages_en.properties")
public class CategoriesController {

    private final CategoriesService categoriesService;
    private final StorageService storageService;

    @Autowired
    public CategoriesController(
            @Value("${greeting}") String myMessage,
            CategoriesService categoriesService,
            StorageService storageService
    ) {
        this.categoriesService = categoriesService;
        this.storageService = storageService;
        System.out.println("CategoriesController Constructor: " + myMessage);
    }

    @GetMapping()
    public Page<CategoryEntity> getCategories(PageCriteria criteria) {

        return categoriesService.findCategories(criteria.asPageable())
                .map(category -> {
                    String url = UrlGenerator.fileNameToUrl(category.getImageName());
                    category.setImageName(url);
                    return category;
                });
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategory(@PathVariable UUID id) {
        CategoryEntity category = categoriesService.findCategory(id);
        category.setImageName(UrlGenerator.fileNameToUrl(category.getImageName()));
        return category;
    }

    @PostMapping()
    public CategoryEntity createCategory(
            @RequestPart("file") MultipartFile file,
            @RequestPart("category") @Valid CategoryCriteria criteria
    ) {

        CategoryEntity entity = categoriesService.saveCategory(
                criteria,
                storageService.save(file)
        );
        String url = UrlGenerator.fileNameToUrl(entity.getImageName());
        entity.setImageName(url);
        return entity;
    }

    @PutMapping("/{id}")
    public CategoryEntity updateCategory(
            @PathVariable UUID id,
            @RequestPart("file") MultipartFile file,
            @RequestPart("category") @Valid CategoryCriteria criteria
    ) {

        String existingImageName = categoriesService.findCategoryImageName(id);
        String imageName = storageService.replaceIfExists(existingImageName, file);
        CategoryEntity entity = categoriesService.updateCategory(id, criteria, imageName);

        String url = UrlGenerator.fileNameToUrl(entity.getImageName());
        entity.setImageName(url);
        return entity;
    }

    @DeleteMapping("/{id}")
    public TransactionDto deleteCategory(@PathVariable UUID id) {

        CategoryEntity category = categoriesService.findCategory(id);
        storageService.deleteIfExists(category.getImageName());
        categoriesService.deleteCategory(id);
        return new TransactionDto("Category: " + id.toString() + " deleted successfully");
    }

}
