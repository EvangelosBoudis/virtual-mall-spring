package com.nativeboyz.vmall.repositories.categories;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import com.nativeboyz.vmall.models.entities.QCategoryEntity;
import com.nativeboyz.vmall.tools.QuerydslRepository;

import java.util.UUID;

public class CategoriesQuerydslRepositoryImpl extends QuerydslRepository<CategoryEntity> implements CategoriesQuerydslRepository {

    public CategoriesQuerydslRepositoryImpl() {
        super(CategoryEntity.class);
    }

    /*
     * JpaRepository Version:
     * @Query("SELECT c.imageName FROM CategoryEntity AS c WHERE c.id = :id")
     * String findImageNameByCategoryId(@Param("id") UUID id);
     * */

    @Override
    public String findImageNameByCategoryId(UUID categoryId) {
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        return from(category)
                .select(category.imageName)
                .where(category.id.eq(categoryId))
                .fetchOne();
    }

}
