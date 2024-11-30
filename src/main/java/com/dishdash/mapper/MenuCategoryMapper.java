package com.dishdash.mapper;

import com.dishdash.dto.MenuCategoryRequestDto;
import com.dishdash.dto.MenuCategoryResponseDTO;
import com.dishdash.dto.MenuItemResponseDTO;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;

public class MenuCategoryMapper {

    public static MenuCategoryResponseDTO convertToMenuCategoryResponse(MenuCategory menuCategory) {
        MenuCategoryResponseDTO menuCategoryResponse = new MenuCategoryResponseDTO();
        menuCategoryResponse.setId(menuCategory.getId());
        menuCategoryResponse.setName(menuCategory.getName());
        menuCategoryResponse.setImageUrl(menuCategory.getImageUrl());

        return menuCategoryResponse;
    }
}
