package com.cs3773.grocery.manager.sweproject.service;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import com.cs3773.grocery.manager.sweproject.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getFilteredAndSortedItems(String search, String sort, String order, Boolean availableOnly) {
        List<Item> items;

        // Step 1: Filter by availability and/or search term
        if (search != null && !search.isEmpty()) {
            if (Boolean.TRUE.equals(availableOnly)) {
                items = itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndItemQuantityGreaterThan(
                        search, search, 0
                );
            } else {
                items = itemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
            }
        } else {
            if (Boolean.TRUE.equals(availableOnly)) {
                items = itemRepository.findByItemQuantityGreaterThan(0);
            } else {
                items = itemRepository.findAll();
            }
        }

        // Step 2: Sort in memory
        if (sort != null && order != null) {
            Comparator<Item> comparator = null;

            if (sort.equalsIgnoreCase("price")) {
                comparator = Comparator.comparing(Item::getItemPrice);
            } else if (sort.equalsIgnoreCase("quantity")) {
                comparator = Comparator.comparing(Item::getItemQuantity);
            }

            if (comparator != null) {
                if (order.equalsIgnoreCase("desc")) {
                    comparator = comparator.reversed();
                }
                items.sort(comparator);
            }
        }

        return items;
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    public Item createItem(Item item) {
        // Optionally validate fields here
        return itemRepository.save(item);
    }

    public Item updateItem(Integer id, Item updatedItem) {
        Item existing = getItemById(id);

        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setImageID(updatedItem.getImageID());
        existing.setItemPrice(updatedItem.getItemPrice());
        existing.setItemQuantity(updatedItem.getItemQuantity());
        existing.setDiscountCode(updatedItem.getDiscountCode());
        existing.setIsOnSale(updatedItem.getIsOnSale());

        return itemRepository.save(existing);
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }
}
