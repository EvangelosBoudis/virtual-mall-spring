package com.nativeboyz.vmall.repositories.categories;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, UUID> {

    @Query("SELECT c.imageName FROM CategoryEntity AS c WHERE c.id = :id")
    String findImageNameByCategoryId(@Param("id") UUID id);

}
