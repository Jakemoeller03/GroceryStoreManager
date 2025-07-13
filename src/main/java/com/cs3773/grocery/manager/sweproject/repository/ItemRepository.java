package com.cs3773.grocery.manager.sweproject.repository;

import com.cs3773.grocery.manager.sweproject.objects.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    // Search by name or description (case-insensitive)
    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descriptionKeyword);

    // Search by name or description AND only return available items (quantity > 0)
    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndItemQuantityGreaterThan(
            String nameKeyword,
            String descriptionKeyword,
            int quantity
    );

    // Get only items that are available (quantity > 0)
    List<Item> findByItemQuantityGreaterThan(int quantity);
}
