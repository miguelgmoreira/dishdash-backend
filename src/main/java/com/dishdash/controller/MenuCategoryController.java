package com.dishdash.controller;

import com.dishdash.dto.*;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;
import com.dishdash.mapper.MenuCategoryMapper;
import com.dishdash.mapper.MenuItemMapper;
import com.dishdash.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu-categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @Autowired
    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<MenuCategoryWithItemCountDTO>> findAll() {
        List<MenuCategoryWithItemCountDTO> menuCategories = menuCategoryService.findAll();
        return new ResponseEntity<>(menuCategories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MenuCategoryResponseDTO> save(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile file
            ) {
        System.out.println("Recebido: " + name + ", " + file.getOriginalFilename());

        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setName(name);

        MenuCategory savedMenuCategory = menuCategoryService.save(menuCategory, file);

        MenuCategoryResponseDTO savedMenuCategoryDTO = MenuCategoryMapper.convertToMenuCategoryResponse(savedMenuCategory);

        return new ResponseEntity<>(savedMenuCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuCategoryResponseDTO> update(
            @PathVariable int id,
            @ModelAttribute MenuCategoryRequestDto menuCategory) {
        MenuCategory updatedMenuCategory = menuCategoryService.update(id, menuCategory);
        MenuCategoryResponseDTO savedMenuCategoryDTO = MenuCategoryMapper.convertToMenuCategoryResponse(updatedMenuCategory);
        return new ResponseEntity<>(savedMenuCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteById(@PathVariable int id) {
        menuCategoryService.deleteById(id);
        MessageResponseDto responseMessage = new MessageResponseDto("Menu category with ID " + id + " was successfully deleted.");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
