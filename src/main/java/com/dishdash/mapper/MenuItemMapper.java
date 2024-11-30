package com.dishdash.mapper;

import com.dishdash.dto.MenuCategoryResponseDTO;
import com.dishdash.dto.MenuItemRequestDto;
import com.dishdash.dto.MenuItemResponseDTO;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;

public class MenuItemMapper {

    public static MenuItemResponseDTO convertToMenuItemResponse(MenuItem menuItem) {
        MenuCategoryResponseDTO menuCategoryResponseDTO = new MenuCategoryResponseDTO();
        menuCategoryResponseDTO.setId(menuItem.getCategory().getId());
        menuCategoryResponseDTO.setName(menuItem.getCategory().getName());
        menuCategoryResponseDTO.setImageUrl(menuItem.getCategory().getImageUrl());

        MenuItemResponseDTO menuItemResponse = new MenuItemResponseDTO();
        menuItemResponse.setId(menuItem.getId());
        menuItemResponse.setName(menuItem.getName());
        menuItemResponse.setDescription(menuItem.getDescription());
        menuItemResponse.setPrice(menuItem.getPrice());
        menuItemResponse.setDiscountedPrice(menuItem.getDiscountedPrice());
        menuItemResponse.setImageUrl(menuItem.getImageUrl());
        menuItemResponse.setCategory(menuCategoryResponseDTO);

        return menuItemResponse;
    }
}

