package com.hackerrank.sample.repository;

import com.hackerrank.sample.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
