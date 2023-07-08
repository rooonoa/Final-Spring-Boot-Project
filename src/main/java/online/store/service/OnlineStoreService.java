package online.store.service;

import lombok.extern.slf4j.Slf4j;
import online.store.controller.model.OnlineStoreCategory;
import online.store.controller.model.OnlineStoreProductData;
import online.store.controller.model.OnlineStoreUser;
import online.store.dao.OnlineStoreProductDao;
import online.store.dao.OnlineStoreUserDao;
import online.store.dao.OnlineStoreCategoryDao;
import online.store.entity.Category;
import online.store.entity.Product;
import online.store.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
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

        User existingUser = findOrCreateUser(productId, userId); // Find or create the user based on the product ID and user ID

        // Copy the fields from the userData object to the existingUser object
        copyOnlineStoreUserFields(userData, existingUser);

        // Set the product for the user and add the user to the product's users collection
        existingUser.setProduct(product);
        product.getUsers().add(existingUser);

        // Save the user in the database
        User savedUser = onlineStoreUserDao.save(existingUser);

        // Return the saved user as an OnlineStoreUser object
        return new OnlineStoreUser(savedUser);
    }

    private User findOrCreateUser(Long productId, Long userId) {
        // If userId is null, return a new User object
        if (Objects.isNull(userId)) {
            return new User();
        }
        
        // If userId is not null, find or create the user based on the product ID and user ID
        return findUserById(productId, userId);
    }

    private User findUserById(Long productId, Long userId) {
        // Find the user by ID from the database
        User user = onlineStoreUserDao.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID=" + userId + " was not found."));
        
        // Check if the user is not associated with the given product ID
        if (user.getProduct().getProductId() != productId) {
            throw new IllegalArgumentException("The user with ID=" + userId
                    + " is not a user for the product with ID=" + productId + ".");
        }
        
        return user;
    }

    // copy matching fields from userData to existing
	
	private void copyOnlineStoreUserFields(OnlineStoreUser userData, User existingUser) {
    	existingUser.setUserEmail(userData.getUserEmail());
        existingUser.setUserFirstName(userData.getUserFirstName());
        existingUser.setUserLastName(userData.getUserLastName());
        existingUser.setUserAddress(userData.getUserAddress());
    }

	@Transactional
	public OnlineStoreCategory saveCategory(Long productId, OnlineStoreCategory category) {
	    Product product = findProductById(productId); // Find the product by its ID
	    Long categoryId = category.getCategoryId(); // Retrieve the category ID from the OnlineStoreCategory object
	    
	 // Find or create the category based on the product ID and category ID
	    Category existingCategory = findOrCreateCategory(productId, categoryId); 

	    // Copy the fields from the category object to the existingCategory object
	    copyOnlineStoreCategoryFields(category, existingCategory);

	    // Set the product for the category and add the category to the product's categories collection
	    existingCategory.getProducts().add(product);
	    product.getCategories().add(existingCategory);

	    // Save the category in the database
	    Category savedCategory = onlineStoreCategoryDao.save(existingCategory);

	    // Return the saved category as an OnlineStoreCategory object
	    return new OnlineStoreCategory(savedCategory);
	}
	private void copyOnlineStoreCategoryFields(OnlineStoreCategory category, Category existingCategory) {
	    // Copy the category name from the category object to the existingCategory object
	    existingCategory.setCategoryName(category.getCategoryName());
	}

	private Category findOrCreateCategory(Long productId, Long categoryId) {
	    // Check if the categoryId is null
	    if (Objects.isNull(categoryId)) {
	        // Return a new Category object if categoryId is null
	        return new Category();
	    }
	    // Find or create the category based on the productId and categoryId
	    return findCategoryById(productId, categoryId);
	}

	private Category findCategoryById(Long productId, Long categoryId) {
	    // Find the category by ID in the database
	    Category category = onlineStoreCategoryDao.findById(categoryId)
	            .orElseThrow(() -> new NoSuchElementException("Category with ID=" + categoryId + " was not found."));

	    boolean found = false;

	    // Check if the category is a member of the product with the given productId
	    for (Product product : category.getProducts()) {
	        if (product.getProductId() == productId) {
	            found = true;
	            break;
	        }
	    }
	    if (!found) {
	        // Throw an exception if the category is not a member of the product
	        throw new IllegalArgumentException("The Category with ID=" + categoryId
	                + " is not a member of the product with ID=" + productId);
	    }

	    return category;
	}

	@Transactional
	public List<OnlineStoreProductData> retrieveAllProducts() {
	    // Retrieve all products from the database
	    List<Product> products = onlineStoreProductDao.findAll();

	    List<OnlineStoreProductData> result = new LinkedList<>();

	    // Convert each product to OnlineStoreProductData and add it to the result list
	    for (Product product : products) {
	        OnlineStoreProductData ospd = new OnlineStoreProductData(product);

	        // Clear the categories and users collections to avoid unwanted data
	        ospd.getCategories().clear();
	        ospd.getUsers().clear();

	        result.add(ospd);
	    }

	    return result;
	}

	@Transactional(readOnly = false)
	public OnlineStoreProductData retrieveProductById(Long productId) {
	    // Find the product by ID in the database
	    Product product = findProductById(productId);

	    // Convert the product to OnlineStoreProductData and return it
	    return new OnlineStoreProductData(product);
	}

	public void deleteProductById(Long productId) {
	    // Find the product by ID in the database
	    Product product = findProductById(productId);
	    // Delete the product from the database
	    onlineStoreProductDao.delete(product);
	}
}
	
