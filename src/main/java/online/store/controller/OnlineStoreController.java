package online.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import online.store.controller.model.OnlineStoreCategory;
import online.store.controller.model.OnlineStoreProductData;
import online.store.service.OnlineStoreService;
import online.store.controller.model.OnlineStoreUser;
import online.store.entity.Product;

@RestController//let Spring know that this class is the Rest Controller
@RequestMapping("/online_store")//Tells Spring that HTTP request that is mapped on
//methods on this controller must start with "/online_store" 
@Slf4j //This is a Lombok annotation that creates an SLFJ logger

public class OnlineStoreController {
	
	@Autowired//injecting OnlineService as an instance variable
	private OnlineStoreService onlineStoreService;
	
	
	/*
	 * The method maps an HTTP POST request to "/online_store".Pass the contents of the request body as a 
	 * parameter (type OnlineStoreData) to the method. (Use @RequestBody.) The method return a OnlineStoreData object. Log the request. 
	 * Call a method in the service class (saveOnlineStoreProduct) that will insert or modify the online store data.
	 */
	 @PostMapping("/product")
	    public OnlineStoreProductData createOnlineStoreProduct(@RequestBody OnlineStoreProductData productData) {
	        log.info("Received a request to create an online store product: {}", productData);
	        return onlineStoreService.saveOnlineStoreProduct(productData);
	    }
	 
	 /*
	     * Handles HTTP PUT requests to update an existing online store product.
	     *
	     * productId   The ID of the product to update
	     * productData The updated product data received in the request body
	     * @return The updated online store product data
	     */

	    @PutMapping("/product/{productId}")
	    public OnlineStoreProductData updateOnlineStoreProduct(
	    		@PathVariable Long productId, @RequestBody OnlineStoreProductData productData) {
	        log.info("Updating online store product with ID: {}", productId);
	        productData.setProductId(productId);
	        return onlineStoreService.saveOnlineStoreProduct(productData);
	    }
	
	    /*
		 * This method handles an HTTP POST request to add a user to a product.
		 * It takes the product ID as a path variable and the user data as a request body.
		 * It returns the created user data.
		 */
		@PostMapping("/{productId}/user")
		@ResponseStatus(HttpStatus.CREATED)
		public OnlineStoreUser addUserToProduct(
				@PathVariable Long productId, @RequestBody OnlineStoreUser user) {
			log.info("Adding user {} to product with ID: {}", user, productId);
			return onlineStoreService.saveUser(productId, user);
		}

	    
	    /*
		 * This method handles an HTTP PUT request to update an existing online store user.
		 * It takes the product ID and user ID as path variables and the updated user data as a request body.
		 * It returns the updated user data.
		 */
		@PutMapping("/{productId}/user/{userId}")
		public OnlineStoreUser updateOnlineStoreUser(
				@PathVariable Long productId, @PathVariable Long userId, @RequestBody OnlineStoreUser user) {
			log.info("Updating online store user with ID: {}", userId);
			return onlineStoreService.saveUser(productId, user);
		}

		@PostMapping("/{productId}/category")
		@ResponseStatus(code = HttpStatus.CREATED)
		// This method handles an HTTP POST request to add a category to a product.
		// It takes the product ID as a path variable and the category data as a request body.
		// It returns the created category data.
		public OnlineStoreCategory addCategoryToProduct(@PathVariable Long productId,
		        @RequestBody OnlineStoreCategory category) {
		    log.info("Adding category {} to product with ID= {}", category, productId);
		    return onlineStoreService.saveCategory(productId, category);
		}

		@PutMapping("/{productId}/category/{categoryId}")
		// This method handles an HTTP PUT request to update an existing online store category.
		// It takes the product ID and category ID as path variables and the updated category data as a request body.
		// It returns the updated category data.
		public OnlineStoreCategory updateOnlineStoreCategory(
		        @PathVariable Long productId, @PathVariable Long categoryId,
		        @RequestBody OnlineStoreCategory category) {
		    log.info("Updating online store category with ID: {}", categoryId);
		    return onlineStoreService.saveCategory(productId, category);
		}

		@GetMapping("/products")
		public List<OnlineStoreProductData> retrieveAllProducts() {
		    log.info("Retrieving all products");
		    return onlineStoreService.retrieveAllProducts();
		}

		/*
		 * Retrieves a product by its ID.
		 * @param productId The ID of the product to retrieve
		 * @return The product with the specified ID
		 */
		@GetMapping("/{productId}")
		public OnlineStoreProductData retrieveProductById(@PathVariable Long productId) {
		    log.info("Retrieving product with ID={}", productId);
		    return onlineStoreService.retrieveProductById(productId);
		}

		/*
		 * Deletes a product by its ID.
		 * @param productId The ID of the product to delete
		 * @return A map containing a message indicating the deletion
		 */
		@DeleteMapping("/{productId}")
		public Map<String, String> deleteProductById(@PathVariable Long productId) {
		    log.info("Deleting product with ID={}", productId);
		    onlineStoreService.deleteProductById(productId);
		    return Map.of("message", "Product with ID=" + productId + " deleted.");
		}

}










