package com.dishdash.service;

import com.dishdash.dto.MenuItemRequestDto;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;
import com.dishdash.exception.ResourceNotFoundException;
import com.dishdash.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuCategoryService menuCategoryService;
    private final S3Service s3Service;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, S3Service s3Service, MenuCategoryService menuCategoryService) {
        this.menuItemRepository = menuItemRepository;
        this.s3Service = s3Service;
        this.menuCategoryService = menuCategoryService;
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findByFilters(String search, Long categoryId) {
        if (search == null && categoryId == null) {
            return findAll();
        }
        if (search != null && categoryId == null) {
            return menuItemRepository.findByNameContainingIgnoreCase(search);
        }
        if (search == null) {
            return menuItemRepository.findByCategoryId(categoryId);
        }
        return menuItemRepository.findByNameContainingIgnoreCaseAndCategoryId(search, categoryId);
    }

    @Override
    public MenuItem findById(int id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "id", id));
    }

    @Override
    public List<MenuItem> findByCategoryId(int categoryId) {
        return List.of();
    }

    @Override
    @Transactional
    public MenuItem save(MenuItem menuItem, MultipartFile file) {
        try {
            String fileName = "menu-items/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String imageUrl = s3Service.uploadImage(file.getInputStream(), fileName, file.getContentType());

            menuItem.setImageUrl(imageUrl);

            return menuItemRepository.save(menuItem);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar item de menu com imagem", e);
        }
    }

    public MenuItem update(int id, MenuItemRequestDto menuItemDto) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "ID", id));

        existingMenuItem.setName(menuItemDto.getName());
        existingMenuItem.setDescription(menuItemDto.getDescription());
        existingMenuItem.setPrice(menuItemDto.getPrice());
        existingMenuItem.setDiscountedPrice(menuItemDto.getDiscountedPrice());

        MenuCategory category = menuCategoryService.findById(menuItemDto.getCategoryId());
        existingMenuItem.setCategory(category);

        // Se houver o upadte da imagem altera o endereço
        if (menuItemDto.getImage() != null && !menuItemDto.getImage().isEmpty()) {
            try {
                String fileName = "menu-items/" + System.currentTimeMillis() + "_" + menuItemDto.getImage().getOriginalFilename();
                String fileUrl = s3Service.uploadImage(menuItemDto.getImage().getInputStream(), fileName, menuItemDto.getImage().getContentType());
                existingMenuItem.setImageUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem", e);
            }
        }

        return menuItemRepository.save(existingMenuItem);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("MenuItem", "id", id);
        }
        menuItemRepository.deleteById(id);
    }
}
