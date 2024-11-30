package com.dishdash.service;

import com.dishdash.dto.MenuCategoryRequestDto;
import com.dishdash.dto.MenuCategoryWithItemCountDTO;
import com.dishdash.entity.MenuCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuCategoryService {

    List<MenuCategoryWithItemCountDTO> findAll();

    MenuCategory findById(int id);

    MenuCategory save(MenuCategory menuItem, MultipartFile file);

    MenuCategory update(int id, MenuCategoryRequestDto menuCategory);

    void deleteById(int id);
}
