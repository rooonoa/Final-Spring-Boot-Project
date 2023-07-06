package online.store.controller.model;

import online.store.entity.Category;
import online.store.entity.Product;
import online.store.entity.User;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnlineStoreProductData {

    private Long productId; // Field to store the product ID (primary key)
    private String productName; // Field to store the product name
    private String productDescription; // Field to store the product description
    private Long productPrice; // Field to store the product price
    private Long productQuantity; // Field to store the product quantity
    private Set<OnlineStoreUser> users = new HashSet<>(); // Set of associated users
    private Set<OnlineStoreCategory> categories = new HashSet<>(); // Set of associated categories

    public OnlineStoreProductData(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productPrice = product.getProductPrice();
        this.productQuantity = product.getProductQuantity();
        
        // Setting users
        for (User user : product.getUsers()) {
            OnlineStoreUser onlineStoreUser = new OnlineStoreUser(user);
            users.add(onlineStoreUser);
        }
        
        // Setting categories
        for (Category category : product.getCategories()) {
            OnlineStoreCategory onlineStoreCategory = new OnlineStoreCategory(category);
            categories.add(onlineStoreCategory);
        }
    }
}





