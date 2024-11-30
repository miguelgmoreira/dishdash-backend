package com.dishdash.dto;

public class MenuCategoryWithItemCountDTO {
    private int id;
    private String name;
    private String imageUrl;
    private Long itemCount;

    public MenuCategoryWithItemCountDTO(int id, String name, String imageUrl, Long itemCount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.itemCount = itemCount;
    }

    // Getters e setters
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
}
