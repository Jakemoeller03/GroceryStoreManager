package com.cs3773.grocery.manager.sweproject.controller;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import com.cs3773.grocery.manager.sweproject.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", ""})
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

    // Update existing item fields
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Integer id, @RequestBody Item updatedItem) {
        return itemService.updateItem(id, updatedItem);
    }

    // Delete an item
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.deleteItem(id);
    }
}

