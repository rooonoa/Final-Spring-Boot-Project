package online.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Indicates that this class is an entity to be mapped to a database table
@Table(name = "category") // Specifies the name of the database table for this entity
@Data // Generates boilerplate code for getters, setters, equals(), hashCode(), and toString() methods
public class Category {

    @Id // Specifies that this field is the primary key for the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the strategy for generating the primary key value
    private Long categoryId; // Field to store the category ID (primary key)

    private String categoryName; // Field to store the category name
    
    @ToString.Exclude // Excludes this field from the toString() method to prevent recursion
    @EqualsAndHashCode.Exclude // Excludes this field from the equals() and hashCode() methods to prevent recursion
    @ManyToMany(mappedBy = "categories" )
    private Set<Product> products = new HashSet<>();
}
