package com.dishdash.controller;

import com.dishdash.dto.MenuItemRequestDto;
import com.dishdash.dto.MenuItemResponseDTO;
import com.dishdash.dto.MessageResponseDto;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;
import com.dishdash.mapper.MenuItemMapper;
import com.dishdash.service.MenuCategoryService;
import com.dishdash.service.MenuCategoryServiceImpl;
import com.dishdash.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuCategoryService menuCategoryService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService, MenuCategoryService menuCategoryService) {
        this.menuItemService = menuItemService;
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId) {
        List<MenuItem> menuItems = menuItemService.findByFilters(search, categoryId);
        List<MenuItemResponseDTO> response = menuItems.stream()
                .map(MenuItemMapper::convertToMenuItemResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> findById(@PathVariable int id) {
        MenuItem menuItem = menuItemService.findById(id);
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> save(
            @RequestParam("image") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam(value = "discountedPrice", required = false) Double discountedPrice,
            @RequestParam("categoryId") Integer categoryId
    ) {
        System.out.println("Recebido: " + name + ", " + price + ", " + categoryId + ", " + file.getOriginalFilename());

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setDiscountedPrice(discountedPrice);

        MenuCategory category = menuCategoryService.findById(categoryId);
        menuItem.setCategory(category);

        MenuItem savedMenuItem = menuItemService.save(menuItem, file);

        MenuItemResponseDTO savedMenuItemDTO = MenuItemMapper.convertToMenuItemResponse(savedMenuItem);

        return new ResponseEntity<>(savedMenuItemDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> update(
            @PathVariable int id,
            @ModelAttribute MenuItemRequestDto menuItem) {
        MenuItem updatedMenuItem = menuItemService.update(id, menuItem);
        MenuItemResponseDTO savedMenuItemDTO = MenuItemMapper.convertToMenuItemResponse(updatedMenuItem);
        return new ResponseEntity<>(savedMenuItemDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteById(@PathVariable int id) {
        menuItemService.deleteById(id);
        MessageResponseDto responseMessage = new MessageResponseDto("Menu item with ID " + id + " was successfully deleted.");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
