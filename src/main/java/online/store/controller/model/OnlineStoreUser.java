package online.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import online.store.entity.User;

@Data
@NoArgsConstructor
public class OnlineStoreUser {

    private Long userId; // Field to store the user ID
    private String userEmail; // Field to store the user email
    private String userFirstName; // Field to store the user's first name
    private String userLastName; // Field to store the user's last name
    private String userAddress; // Field to store the user's address

    public OnlineStoreUser(User user) {
        this.userId = user.getUserId(); // Assigning the user's ID to the corresponding field
        this.userEmail = user.getUserEmail(); // Assigning the user's email to the corresponding field
        this.userFirstName = user.getUserFirstName(); // Assigning the user's first name to the corresponding field
        this.userLastName = user.getUserLastName(); // Assigning the user's last name to the corresponding field
        this.userAddress = user.getUserAddress();//Assigning the user address to the corresponding field 
        
        
    }
}
