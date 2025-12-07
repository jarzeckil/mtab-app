package com.mtab.mtabapp.repository;

import com.mtab.mtabapp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(Long category_id);
}
