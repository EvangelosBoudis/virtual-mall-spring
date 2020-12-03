package com.nativeboyz.vmall.repositories.test;

import java.util.Map;
import java.util.UUID;

public interface CategoriesRepo {

    Map<String, Object> findById(String fields, UUID categoryId);

}
