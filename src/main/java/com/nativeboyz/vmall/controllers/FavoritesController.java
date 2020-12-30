package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.criteria.FavoriteCriteria;
import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.services.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private final ProductsService productsService;

    @Autowired
    public FavoritesController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping()
    public FavoriteEntity saveFavorite(
            @RequestBody @Valid FavoriteCriteria criteria
    ) {
        return productsService.saveFavorite(criteria);
    }

}
