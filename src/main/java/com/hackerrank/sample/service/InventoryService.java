package com.hackerrank.sample.service;

import com.hackerrank.sample.excpetion.BadResourceRequestException;
import com.hackerrank.sample.excpetion.NoSuchResourceFoundException;
import com.hackerrank.sample.model.Inventory;
import com.hackerrank.sample.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventory(Long itemId) {
        return inventoryRepository.findById(itemId).orElseThrow(()-> new NoSuchResourceFoundException("Inventory with given id " + itemId + " not found."));
    }

    public Inventory addInventory(Inventory inventory) {

        try{
            if (getInventory(inventory.getSkuId()) == null){
                return inventoryRepository.save(inventory);
            }else {
                throw new BadResourceRequestException("Inventory with same id:" + inventory.getSkuId() + " exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public Inventory updateInventory(Long itemId,Inventory inventory) {

        try{
            if (getInventory(itemId) != null){
                return inventoryRepository.save(inventory);
            }else {
                throw new BadResourceRequestException("Inventory with id:" + inventory.getSkuId() + " doesn't exists.");
            }
        }catch (Exception e){
            throw new BadResourceRequestException(e.getMessage());
        }
    }

    public void deleteItem(Long itemId) {
        if (getInventory(itemId) != null){
            inventoryRepository.deleteById(itemId);
        }else {
            throw new NoSuchResourceFoundException("Inventory with given id " + itemId + " not found.");
        }
    }

    public void deleteItems() {
        inventoryRepository.deleteAllInBatch();
    }
}
