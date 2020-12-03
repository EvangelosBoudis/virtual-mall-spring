package com.nativeboyz.vmall.repositories.categories;

import com.nativeboyz.vmall.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriesRepository extends JpaRepository<CategoryEntity, UUID> {

}
