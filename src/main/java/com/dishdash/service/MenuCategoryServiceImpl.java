package com.dishdash.service;

import com.dishdash.dto.MenuCategoryRequestDto;
import com.dishdash.dto.MenuCategoryWithItemCountDTO;
import com.dishdash.dto.MenuItemRequestDto;
import com.dishdash.entity.MenuCategory;
import com.dishdash.entity.MenuItem;
import com.dishdash.exception.ResourceNotFoundException;
import com.dishdash.repository.MenuCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final S3Service s3Service;

    @Autowired
    public MenuCategoryServiceImpl(MenuCategoryRepository menuCategoryRepository, S3Service s3Service) {
        this.menuCategoryRepository = menuCategoryRepository;
        this.s3Service = s3Service;
    }

    @Override
    public List<MenuCategoryWithItemCountDTO> findAll() {
        return menuCategoryRepository.findAllWithItemCount();
    }

    @Override
    public MenuCategory findById(int id) {
        return menuCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    @Transactional
    public MenuCategory save(MenuCategory menuCategory, MultipartFile file) {
        try {
            String fileName = "menu-categories/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String imageUrl = s3Service.uploadImage(file.getInputStream(), fileName, file.getContentType());

            menuCategory.setImageUrl(imageUrl);

            return menuCategoryRepository.save(menuCategory);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar categoria com imagem", e);
        }
    }

    public MenuCategory update(int id, MenuCategoryRequestDto menuCategoryDto) {
        MenuCategory existingMenuCategory = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuCategory", "ID", id));

        existingMenuCategory.setName(menuCategoryDto.getName());

        // Se houver o update da imagem altera o endereço
        if (menuCategoryDto.getImage() != null && !menuCategoryDto.getImage().isEmpty()) {
            try {
                String fileName = "menu-categories/" + System.currentTimeMillis() + "_" + menuCategoryDto.getImage().getOriginalFilename();
                String fileUrl = s3Service.uploadImage(menuCategoryDto.getImage().getInputStream(), fileName, menuCategoryDto.getImage().getContentType());
                existingMenuCategory.setImageUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem", e);
            }
        }

        return menuCategoryRepository.save(existingMenuCategory);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        if (!menuCategoryRepository.existsById(id)) {
            throw new RuntimeException("Resource not found");
        }
        menuCategoryRepository.deleteById(id);
    }
}
