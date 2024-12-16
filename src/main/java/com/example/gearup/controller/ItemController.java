package com.example.gearup.controller;

import com.example.gearup.data.Item;
import com.example.gearup.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer itemId) {
        return itemService.getItemById(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/items/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(itemService.getItemsByUserId(userId));
    }

    @GetMapping("/items/search")
    public ResponseEntity<List<Item>> getItemsByName(@RequestParam String name) {
        return ResponseEntity.ok(itemService.getItemsByName(name));
    }

    @GetMapping("/items/sort/price-low-to-high")
    public ResponseEntity<List<Item>> sortByPriceLowToHigh() {
        return ResponseEntity.ok(itemService.sortByPriceLowToHigh());
    }

    @GetMapping("/items/sort/price-high-to-low")
    public ResponseEntity<List<Item>> sortByPriceHighToLow() {
        return ResponseEntity.ok(itemService.sortByPriceHighToLow());
    }

    @GetMapping("/items/location")
    public ResponseEntity<List<Item>> findByLocationRange(
            @RequestParam String point, @RequestParam double distance) {
        return ResponseEntity.ok(itemService.findByLocationRange(point, distance));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Item>> getItemsByLocation(
            @RequestParam String locationName,
            @RequestParam double distance) {
        try {
            List<Item> items = itemService.getItemsByLocation(locationName, distance);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        String location = itemService.getCoordinates(item.getCity()); // Convert city to POINT
        item.setItemLocation(location);
        Item savedItem = itemService.createItem(item);
        return ResponseEntity.ok(savedItem);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable Integer itemId, @RequestBody Item updatedItem) {
        Optional<Item> existingItem = itemService.getItemById(itemId);
        if (existingItem.isPresent()) {
            updatedItem.setItemLocation(itemService.getCoordinates(updatedItem.getCity())); // Update location if city changes
            Item savedItem = itemService.updateItem(itemId, updatedItem);
            return ResponseEntity.ok(savedItem);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer itemId) {
        if (itemService.deleteItem(itemId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get items by name and location, sorted by distance
    @GetMapping("/search-by-location-and-name")
    public ResponseEntity<List<Item>> getItemsByLocationAndName(
            @RequestParam String locationName,
            @RequestParam String itemName,
            @RequestParam double distance) {
        try {
            List<Item> items = itemService.getItemsByLocationAndName(locationName, itemName, distance);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("items/{itemId}/add-image")
    public ResponseEntity<Item> addImageToItem(
            @PathVariable Integer itemId,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            Item updatedItem = itemService.addImageToItem(itemId, imageFile);
            return ResponseEntity.ok(updatedItem);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

