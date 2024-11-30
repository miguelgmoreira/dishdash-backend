package com.dishdash.dto;

import com.dishdash.entity.MenuCategory;

public class MenuItemResponseDTO {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Double discountedPrice;
    private String imageUrl;
    private MenuCategoryResponseDTO category;

    public MenuItemResponseDTO() {
    }

    public MenuItemResponseDTO(int id, String name, String description, Double price, Double discountedPrice, String imageUrl, MenuCategoryResponseDTO category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MenuCategoryResponseDTO getCategory() {
        return category;
    }

    public void setCategory(MenuCategoryResponseDTO category) {
        this.category = category;
    }
}
