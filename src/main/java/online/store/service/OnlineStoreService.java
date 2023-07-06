package online.store.service;

import lombok.extern.slf4j.Slf4j;

import online.store.controller.model.OnlineStoreProductData;
import online.store.controller.model.OnlineStoreUser;
import online.store.dao.OnlineStoreProductDao;
import online.store.dao.OnlineStoreUserDao;
import online.store.dao.OnlineStoreCategoryDao;
import online.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Slf4j
// This is the service class that handles online store operations
public class OnlineStoreService {

    private final OnlineStoreProductDao onlineStoreProductDao;
    private final OnlineStoreUserDao onlineStoreUserDao;
    private final OnlineStoreCategoryDao onlineStoreCategoryDao;

    @Autowired
    // Constructor-based dependency injection using Autowired annotation
    public OnlineStoreService(OnlineStoreProductDao onlineStoreProductDao,
    		OnlineStoreUserDao onlineStoreUserDao, OnlineStoreCategoryDao onlineStoreCategoryDao) {
        this.onlineStoreProductDao = onlineStoreProductDao;
        this.onlineStoreUserDao = onlineStoreUserDao;
        this.onlineStoreCategoryDao = onlineStoreCategoryDao;
    }

    /*
     * This method saves the online store product data.
     * If the product ID is null, it creates a new product.
     * If the product ID is not null, it finds the product by ID in the database.
     * It then copies the matching fields from the product data to the product object.
     * Finally, it saves the product in the database and returns the saved product data.
     */
    public OnlineStoreProductData saveOnlineStoreProduct(OnlineStoreProductData productData) {
        Long productId = productData.getProductId();
        Product product;

        if (productId == null) {
            product = findOrCreateProduct(productData);
        } else {
            product = findProductById(productId);
            if (product == null) {
                throw new NoSuchElementException("Product not found with ID: " + productId);
            }
        }

        copyOnlineStoreFields(productData, product);

        Product savedProduct = onlineStoreProductDao.save(product);

        log.info("Saved online store product: {}", savedProduct);

        return new OnlineStoreProductData(savedProduct);
    }

 // Find a product by ID in the database
    private Product findProductById(Long productId) {
        return onlineStoreProductDao.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with ID=" + productId + " was not found."));
    }

 // Find or create a new product in the database
    private Product findOrCreateProduct(OnlineStoreProductData productData) {
        Long productId = productData.getProductId();
        Product product;

        if (Objects.isNull(productId)) {
            product = new Product(); // Create a new product
        } else {
            product = findProductById(productId);
            if (product == null) {
                throw new NoSuchElementException("Product not found with ID: " + productId);
            }
        }

        return product;
    }

    // Copy matching fields from productData to product
    private void copyOnlineStoreFields(OnlineStoreProductData productData, Product product) {
        // Implement the logic to copy matching fields from productData to product
        product.setProductId(productData.getProductId());
        product.setProductName(productData.getProductName());
        product.setProductDescription(productData.getProductDescription());
        product.setProductPrice(productData.getProductPrice());
        product.setProductQuantity(productData.getProductQuantity());
    }
    

    @Transactional(readOnly = false)
    public OnlineStoreUser saveUser(Long productId, OnlineStoreUser userData) {
        Product product = findProductById(productId); // Find the product by its ID
        Long userId = userData.getUserId(); // Retrieve the user ID from the OnlineStoreUser object

        OnlineStoreUser existingUser = findOrCreateUser(productId, userId); // Find or create the user based on the product ID and user ID

        // Copy the fields from the userData object to the existingUser object
        copyOnlineStoreUserFields(userData, existingUser);

        // Set the product for the user and add the user to the product's users collection
        existingUser.setProduct(product);
        product.getUsers().add(existingUser);

        // Save the user in the database
        OnlineStoreUser savedUser = onlineStoreUserDao.save(existingUser);

        // Return the saved user as an OnlineStoreUser object
        return new OnlineStoreUser(savedUser);
    }

    private void copyOnlineStoreUserFields(OnlineStoreUser userData, OnlineStoreUser existingUser) {
    	existingUser.setUserEmail(userData.getUserEmail());
        existingUser.setUserFirstName(userData.getUserFirstName());
        existingUser.setUserLastName(userData.getUserLastName());
        existingUser.setUserAddress(userData.getUserAddress());
    }

    private OnlineStoreUser findOrCreateUser(Long productId, Long userId) {
        // TODO: Implement the logic to find or create the user based on the product ID and user ID
        return null;
    }


		
		
		
		
		
		
	
}
