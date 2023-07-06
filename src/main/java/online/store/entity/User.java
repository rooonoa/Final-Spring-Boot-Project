package online.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Indicates that this class is an entity to be mapped to a database table
@Table(name = "user") // Specifies the name of the database table for this entity
@Data // Generates boilerplate code for getters, setters, equals(), hashCode(), and toString() methods
public class User {
	
	@Id // Specifies that this field is the primary key for the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the strategy for generating the primary key value
	private Long userId; // Field to store the user ID (primary key)
	
	@Column(name = "user_email") // Specifies the name of the database column for this field
	private String userEmail; // Field to store the user email
	
	private String userFirstName; // Field to store the user's first name
	private String userLastName; // Field to store the user's last name
	private String userAddress; // Field to store the user's address
	
	@ToString.Exclude // Excludes this field from the toString() method to prevent recursion
	@EqualsAndHashCode.Exclude // Excludes this field from the equals() and hashCode() methods to prevent recursion
	@ManyToOne(cascade = CascadeType.ALL) // Specifies a one-to-many relationship with Product entity
	@JoinColumn(name = "product_id")
    private Product product;
}
