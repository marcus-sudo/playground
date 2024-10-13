package org.example.util;

import org.example.dto.UserDTO;

public class UserUtils {
    private UserUtils() { throw new IllegalStateException("Utility class"); }
    public static void updateUserFields(UserDTO existingUser, UserDTO userDTO) {
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
    }
}
