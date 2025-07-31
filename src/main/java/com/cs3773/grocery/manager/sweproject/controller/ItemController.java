
package com.cs3773.grocery.manager.sweproject.controller;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import com.cs3773.grocery.manager.sweproject.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@CrossOrigin(origins = "http://35.184.41.71/")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Get all items, optionally sorted and filtered
    @GetMapping
    public List<Item> getItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Boolean availableOnly
    ) {
        return itemService.getFilteredAndSortedItems(search, sort, order, availableOnly);
    }

    // Get a single item by ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Integer id) {
        return itemService.getItemById(id);
    }

    // Create a new item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    // Create a new item with image upload
    @PostMapping("/with-image")
    public ResponseEntity<Item> createItemWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("itemPrice") Long itemPrice,
            @RequestParam("itemQuantity") Integer itemQuantity,
            @RequestParam(value = "discountCode", required = false) String discountCode,
            @RequestParam(value = "isOnSale", defaultValue = "false") Boolean isOnSale,
            @RequestParam("image") MultipartFile image,
            HttpServletRequest request) {

        // Debug logging
        System.out.println("=== DEBUG INFO ===");
        System.out.println("Content-Type: " + request.getContentType());
        System.out.println("Request parameters: " + request.getParameterMap().keySet());
        System.out.println("Image is null: " + (image == null));
        if (image != null) {
            System.out.println("Image name: " + image.getOriginalFilename());
            System.out.println("Image size: " + image.getSize());
            System.out.println("Image empty: " + image.isEmpty());
        }
        System.out.println("==================");

        try {
            Item item = new Item(name, null, description, itemPrice, itemQuantity, discountCode, isOnSale);
            Item createdItem = itemService.createItemWithImage(item, image);
            return ResponseEntity.ok(createdItem);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Update existing item fields
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Integer id, @RequestBody Item updatedItem) {
        return itemService.updateItem(id, updatedItem);
    }

    // Update item with new image
    @PutMapping("/{id}/with-image")
    public ResponseEntity<Item> updateItemWithImage(
            @PathVariable Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "itemPrice", required = false) Long itemPrice,
            @RequestParam(value = "itemQuantity", required = false) Integer itemQuantity,
            @RequestParam(value = "discountCode", required = false) String discountCode,
            @RequestParam(value = "isOnSale", required = false) Boolean isOnSale,
            @RequestParam("image") MultipartFile image) {

        try {
            Item updatedItem = itemService.updateItemWithImage(id, name, description, itemPrice,
                    itemQuantity, discountCode, isOnSale, image);
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Upload/update image for existing item
    @PostMapping("/{id}/image")
    public ResponseEntity<Item> uploadItemImage(@PathVariable Integer id,
                                                @RequestParam("image") MultipartFile image) {
        try {
            Item updatedItem = itemService.updateItemImage(id, image);
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete an item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.deleteItem(id);
    }
}