package online.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import online.store.entity.Category;

@Data
@NoArgsConstructor
public class OnlineStoreCategory {

    private Long categoryId; // Unique identifier for each category
    private String categoryName; // Name of the category

    public OnlineStoreCategory(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}