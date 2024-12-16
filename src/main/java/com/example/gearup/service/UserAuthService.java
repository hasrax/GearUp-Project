package com.example.gearup.service;

import com.example.gearup.data.UserAuth;
import com.example.gearup.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService
{
    @Autowired
    private UserAuthRepository userAuthRepository;
    private ImageUploadService imageUploadService;
    private PasswordEncoder passwordEncoder;

    public List<UserAuth> getAllUserAuth()
    {
        return userAuthRepository.findAll();
    }
    public UserAuth createUserAuth(UserAuth userAuth) {
        return userAuthRepository.save(userAuth);
    }

     public UserAuth getUserAuthById(Integer userId)
     {
         Optional<UserAuth> UserAuth=userAuthRepository.findById(userId);
         if(UserAuth.isPresent())
         {
             return UserAuth.get();
         }

         return null;
     }

    public List<UserAuth> getUserAuthByEmail(String email)
    {
            return userAuthRepository.findByEmail(email);
    }
    public UserAuth updateUserAuth(UserAuth userAuth)
    {
        return userAuthRepository.save(userAuth);
    }

    public boolean deleteUserAuth(Integer userId)
    {
        Optional<UserAuth> userAuth=userAuthRepository.findById(userId);
        if(userAuth.isPresent())
        {
            userAuthRepository.deleteById(userId);
            return true;
        }

        return false;

    }

    // Add Image from Device
    public UserAuth addImageFromDevice(Integer userId, MultipartFile imageFile) throws IOException {
        Optional<UserAuth> optionalUser = userAuthRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id " + userId);
        }

        UserAuth user = optionalUser.get();

        // Delete existing image if present
        if (user.getImage() != null && !user.getImage().isEmpty()) {
            imageUploadService.deleteImage(user.getImage());
        }

        // Upload new image
        String imagePath = imageUploadService.uploadImage(imageFile, "users");
        user.setImage(imagePath);

        return userAuthRepository.save(user);
    }

    // Remove Image
    public UserAuth removeImage(Integer userId) {
        Optional<UserAuth> optionalUser = userAuthRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with id " + userId);
        }

        UserAuth user = optionalUser.get();

        /*// Delete the image from the file system
        if (user.getImage() != null && !user.getImage().isEmpty()) {
            imageUploadService.deleteImage(user.getImage());
            user.setImage(null);
        }*/

        return userAuthRepository.save(user);
    }

    public String logout() {
        // Optional: Add logging or any other operations needed before logout
        return "Logout successful";
    }
}
