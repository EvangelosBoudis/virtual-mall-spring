package com.nativeboyz.vmall.controllers;

import com.nativeboyz.vmall.services.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/searches")
public class SearchesController {

    private final ProductsService productsService;

    @Autowired
    public SearchesController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping()
    public List<String> findMatch(
            @RequestParam("searchKey") @NotNull String searchKey
    ) {
        // TODO: Implement
        return null;
    }

}
