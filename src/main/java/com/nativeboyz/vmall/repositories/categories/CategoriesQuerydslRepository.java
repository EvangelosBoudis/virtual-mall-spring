package com.nativeboyz.vmall.repositories.categories;

import java.util.UUID;

public interface CategoriesQuerydslRepository {

    String findImageNameByCategoryId(UUID categoryId);

}
