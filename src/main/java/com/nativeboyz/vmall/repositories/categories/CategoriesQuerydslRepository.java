package com.nativeboyz.vmall.repositories.categories;

import java.util.UUID;

public interface CategoriesQuerydslRepository {

    // TODO: Optional
    String findImageNameByCategoryId(UUID categoryId);

}
