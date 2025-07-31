package com.cs3773.grocery.manager.sweproject.service;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import com.cs3773.grocery.manager.sweproject.repository.ItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final GoogleCloudStorageService storageService;

    public ItemService(ItemRepository itemRepository, GoogleCloudStorageService storageService) {
        this.itemRepository = itemRepository;
        this.storageService = storageService;
    }

    public List<Item> getFilteredAndSortedItems(String search, String sort, String order, Boolean availableOnly) {
        List<Item> items = itemRepository.findAll();

        // Filter by search term
        if (search != null && !search.isEmpty()) {
            items = items.stream()
                    .filter(item -> item.getName().toLowerCase().contains(search.toLowerCase()) ||
                            item.getDescription().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by availability
        if (availableOnly != null && availableOnly) {
            items = items.stream()
                    .filter(item -> item.getItemQuantity() > 0)
                    .collect(Collectors.toList());
        }

        // Sort items
        if (sort != null) {
            boolean ascending = order == null || order.equalsIgnoreCase("asc");
            items = sortItems(items, sort, ascending);
        }

        return items;
    }

    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item createItemWithImage(Item item, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String imageUrl = storageService.uploadFile(image);
            item.setImageID(imageUrl);
        }
        return itemRepository.save(item);
    }

    public Item updateItem(Integer id, Item updatedItem) {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }

        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getItemPrice() != null) {
            existingItem.setItemPrice(updatedItem.getItemPrice());
        }
        if (updatedItem.getItemQuantity() != null) {
            existingItem.setItemQuantity(updatedItem.getItemQuantity());
        }
        if (updatedItem.getDiscountCode() != null) {
            existingItem.setDiscountCode(updatedItem.getDiscountCode());
        }
        if (updatedItem.getIsOnSale() != null) {
            existingItem.setIsOnSale(updatedItem.getIsOnSale());
        }
        if (updatedItem.getImageID() != null) {
            existingItem.setImageID(updatedItem.getImageID());
        }

        return itemRepository.save(existingItem);
    }

    public Item updateItemWithImage(Integer id, String name, String description, Long itemPrice,
                                    Integer itemQuantity, String discountCode, Boolean isOnSale,
                                    MultipartFile image) throws IOException {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }

        // Delete old image if exists and new image is provided
        if (image != null && !image.isEmpty() && existingItem.getImageID() != null) {
            String oldFileName = storageService.extractFileNameFromUrl(existingItem.getImageID());
            if (oldFileName != null) {
                storageService.deleteFile(oldFileName);
            }
        }

        // Update fields if provided
        if (name != null) existingItem.setName(name);
        if (description != null) existingItem.setDescription(description);
        if (itemPrice != null) existingItem.setItemPrice(itemPrice);
        if (itemQuantity != null) existingItem.setItemQuantity(itemQuantity);
        if (discountCode != null) existingItem.setDiscountCode(discountCode);
        if (isOnSale != null) existingItem.setIsOnSale(isOnSale);

        // Upload new image if provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = storageService.uploadFile(image);
            existingItem.setImageID(imageUrl);
        }

        return itemRepository.save(existingItem);
    }

    public Item updateItemImage(Integer id, MultipartFile image) throws IOException {
        Item existingItem = itemRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }

        // Delete old image if exists
        if (existingItem.getImageID() != null) {
            String oldFileName = storageService.extractFileNameFromUrl(existingItem.getImageID());
            if (oldFileName != null) {
                storageService.deleteFile(oldFileName);
            }
        }

        // Upload new image
        String imageUrl = storageService.uploadFile(image);
        existingItem.setImageID(imageUrl);

        return itemRepository.save(existingItem);
    }

    public void deleteItem(Integer id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            // Delete image from storage if exists
            if (item.getImageID() != null) {
                String fileName = storageService.extractFileNameFromUrl(item.getImageID());
                if (fileName != null) {
                    storageService.deleteFile(fileName);
                }
            }
            itemRepository.deleteById(id);
        }
    }

    private List<Item> sortItems(List<Item> items, String sort, boolean ascending) {
        return items.stream()
                .sorted((a, b) -> {
                    int comparison;
                    switch (sort.toLowerCase()) {
                        case "name":
                            comparison = a.getName().compareToIgnoreCase(b.getName());
                            break;
                        case "price":
                            comparison = a.getItemPrice().compareTo(b.getItemPrice());
                            break;
                        case "quantity":
                            comparison = a.getItemQuantity().compareTo(b.getItemQuantity());
                            break;
                        default:
                            comparison = a.getItemID().compareTo(b.getItemID());
                    }
                    return ascending ? comparison : -comparison;
                })
                .collect(Collectors.toList());
    }
}