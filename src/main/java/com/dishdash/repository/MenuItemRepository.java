package com.dishdash.repository;

import com.dishdash.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByCategoryId(Long categoryId);
    List<MenuItem> findByNameContainingIgnoreCaseAndCategoryId(String name, Long categoryId);
}
