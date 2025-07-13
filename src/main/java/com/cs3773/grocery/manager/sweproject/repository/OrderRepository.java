package com.cs3773.grocery.manager.sweproject.repository;

import com.cs3773.grocery.manager.sweproject.objects.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<order, Integer> {

    // Optional: add custom queries if needed
    List<order> findByCustomerID(int customerID);

}
