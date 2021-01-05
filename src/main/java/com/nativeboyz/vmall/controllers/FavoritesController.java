package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.models.criteria.FavoriteCriteria;
import com.nativeboyz.vmall.models.entities.FavoriteEntity;
import com.nativeboyz.vmall.services.favorites.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @Autowired
    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @PostMapping()
    public FavoriteEntity saveFavorite(
            @RequestBody @Valid FavoriteCriteria criteria
    ) {
        return favoritesService
                .saveFavorite(criteria)
                .orElseThrow();
    }

}
