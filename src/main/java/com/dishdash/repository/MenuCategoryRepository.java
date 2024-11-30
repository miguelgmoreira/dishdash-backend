package com.dishdash.repository;

import com.dishdash.dto.MenuCategoryWithItemCountDTO;
import com.dishdash.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {

    @Query("SELECT new com.dishdash.dto.MenuCategoryWithItemCountDTO(mc.id, mc.name, mc.imageUrl, COUNT(i)) " +
            "FROM MenuCategory mc LEFT JOIN mc.items i " +
            "GROUP BY mc.id, mc.name, mc.imageUrl")
    List<MenuCategoryWithItemCountDTO> findAllWithItemCount();
}
