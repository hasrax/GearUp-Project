package com.example.gearup.service;

import com.example.gearup.data.Item;
import com.example.gearup.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private final String uploadDir = "uploads/items"; // Directory to store uploaded images

    @Autowired
    private RestTemplate restTemplate;
    //AIzaSyAFbfL-9p_B-oFlf4rUa__JxaneO36qMtE
    private final String apiKey = "AIzaSyAFbfL-9p_B-oFlf4rUa__JxaneO36qMtE"; // Replace with your API key

    public String getCoordinates(String city) {
        // Google Maps Geocoding API URL
        String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=" + apiKey;

        try {
            // Call the geocoding API
            Map response = restTemplate.getForObject(apiUrl, Map.class);

            if (response != null && response.get("status").equals("OK")) {
                // Extract latitude and longitude from the response
                Map location = (Map) ((Map) ((Map) ((Map[]) response.get("results"))[0]).get("geometry")).get("location");
                String latitude = location.get("lat").toString();
                String longitude = location.get("lng").toString();

                // Return as POINT format (for MySQL spatial data)
                return "POINT(" + longitude + " " + latitude + ")";
            } else {
                throw new RuntimeException("Coordinates not found for city: " + city);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching coordinates for city: " + city, e);
        }
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Integer itemId) {
        return itemRepository.findById(itemId);
    }

    public List<Item> getItemsByUserId(Integer userId) {
        return itemRepository.findByOwner_UserId(userId);
    }

    public List<Item> getItemsByName(String name) {
        return itemRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Item> sortByPriceLowToHigh() {
        return itemRepository.sortByPriceLowToHigh();
    }

    public List<Item> sortByPriceHighToLow() {
        return itemRepository.sortByPriceHighToLow();
    }

    public List<Item> findByLocationRange(String point, double distance) {
        return itemRepository.findByLocationRange(point, distance);
    }

    public List<Item> getItemsByLocation(String locationName, double distance) {
        // Convert location name to POINT format using geocoding
        String point = getCoordinates(locationName);

        // Query database for items within the specified distance
        return itemRepository.findByLocationRange(point, distance);
    }
    public Item createItem(Item item) {
        // Convert city to POINT
        String location = getCoordinates(item.getCity());
        item.setItemLocation(location);
        return itemRepository.save(item);
    }

    public Item updateItem(Integer itemId, Item updatedItem) {
        Optional<Item> existingItem = itemRepository.findById(itemId);
        if (existingItem.isPresent()) {
            Item item = existingItem.get();
            item.setName(updatedItem.getName());
            item.setCategory(updatedItem.getCategory());
            item.setOwner(updatedItem.getOwner());
            item.setDistrict(updatedItem.getDistrict());
            item.setCity(updatedItem.getCity());
            item.setPostalcode(updatedItem.getPostalcode());
            item.setDailyRentalPrice(updatedItem.getDailyRentalPrice());
            item.setWeeklyRentalPrice(updatedItem.getWeeklyRentalPrice());
            item.setMonthlyRentalPrice(updatedItem.getMonthlyRentalPrice());
            item.setImages(updatedItem.getImages());
            item.setSubCategory(updatedItem.getSubCategory());
            // Update location if city changes
            String location = getCoordinates(updatedItem.getCity());
            item.setItemLocation(location);
            return itemRepository.save(item);
        }
        return null;
    }

    public boolean deleteItem(Integer itemId) {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }
    // Find items by name and location
    public List<Item> getItemsByLocationAndName(String locationName, String itemName, double distance) {
        // Convert location name to POINT format using geocoding
        String point = getCoordinates(locationName);

        // Query database for items matching the name and within the specified distance
        return itemRepository.findByNameAndLocation(itemName, point, distance);
    }

    public Item addImageToItem(Integer itemId, MultipartFile imageFile) throws IOException {
        // Find the item by ID
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Item not found");
        }

        Item item = optionalItem.get();

        // Validate the image file
        if (imageFile.isEmpty()) {
            throw new IOException("File is empty");
        }

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name
        String originalFilename = imageFile.getOriginalFilename();
        String fileName = "item_" + itemId + "_" + System.currentTimeMillis() + "_" + originalFilename;

        // Save the file to the directory
        Path targetPath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), targetPath);

        // Update the item's images field (you can store multiple images in a comma-separated string)
        String existingImages = item.getImages();
        if (existingImages == null || existingImages.isEmpty()) {
            item.setImages(fileName);
        } else {
            item.setImages(existingImages + "," + fileName);
        }

        // Save the updated item
        return itemRepository.save(item);
    }
}
