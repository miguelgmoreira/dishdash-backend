package com.dishdash.service;

import com.dishdash.dto.MenuItemRequestDto;
import com.dishdash.entity.MenuItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuItemService {

    List<MenuItem> findByFilters(String search, Long categoryId);

    MenuItem findById(int id);

    List<MenuItem> findByCategoryId(int categoryId);

    MenuItem save(MenuItem menuItem, MultipartFile file);

    MenuItem update(int id, MenuItemRequestDto menuItem);

    void deleteById(int id);
}