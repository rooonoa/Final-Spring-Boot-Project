package online.store.entity;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Indicates that this class is an entity to be mapped to a database table
@Table(name = "product") // Specifies the name of the database table for this entity
@Data // Generates boilerplate code for getters, setters, equals(), hashCode(), and toString() methods
public class Product {
	
	@Id // Specifies that this field is the primary key for the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the strategy for generating the primary key value
	private Long productId; // Field to store the product ID (primary key)
	
	private String productName; // Field to store the product name
	private String productDescription; // Field to store the product description
	private Long productPrice; // Field to store the product price
	private Long productQuantity; // Field to store the product quantity
	
	@ToString.Exclude // Excludes this field from the toString() method to prevent recursion
	@EqualsAndHashCode.Exclude // Excludes this field from the equals() and hashCode() methods to prevent recursion
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // Specifies a one-to-many relationship with User entity
	private Set<User> users = new HashSet<>(); // Field to store the related users
	
	@ToString.Exclude // Excludes this field from the toString() method to prevent recursion
	@EqualsAndHashCode.Exclude // Excludes this field from the equals() and hashCode() methods to prevent recursion
	 @ManyToMany
	    @JoinTable(
	        name = "category_product",
	        joinColumns = @JoinColumn(name = "product_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id")
	    )
	    private Set<Category> categories = new HashSet<>();
}
