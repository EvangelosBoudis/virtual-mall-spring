package com.nativeboyz.vmall.services.products;

import com.nativeboyz.vmall.models.criteria.product.ProductCriteria;
import com.nativeboyz.vmall.models.dto.ProductDto;
import com.nativeboyz.vmall.models.dto.ProductInfoDto;
import com.nativeboyz.vmall.models.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductsService {

    ProductDto findProduct(UUID productId, UUID customerId);

    ProductInfoDto findProductInfo(UUID productId, UUID customerId);

    Page<ProductDto> findProducts(Pageable pageable, UUID customerId);

    ProductEntity saveProduct(ProductCriteria criteria, String[] fileNames);

    ProductEntity updateProduct(UUID productId, ProductCriteria criteria, String[] fileNames);

    void deleteProduct(UUID id);

    List<String> findProductImages(UUID productId);

}
