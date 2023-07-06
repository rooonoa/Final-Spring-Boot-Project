package online.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import online.store.controller.model.OnlineStoreProductData;
import online.store.service.OnlineStoreService;

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
	    public OnlineStoreProductData updateOnlineStoreProduct(@PathVariable Long productId, @RequestBody OnlineStoreProductData productData) {
	        log.info("Updating online store product with ID: {}", productId);
	        productData.setProductId(productId);
	        return onlineStoreService.saveOnlineStoreProduct(productData);
	    }
	
	 
	 
}
