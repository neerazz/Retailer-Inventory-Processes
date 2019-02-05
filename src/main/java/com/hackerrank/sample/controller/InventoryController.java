package com.hackerrank.sample.controller;

import com.hackerrank.sample.model.Inventory;
import com.hackerrank.sample.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/item")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getInventory(){
        return inventoryService.getInventory();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventory(@PathVariable("id") Long itemId){
        return inventoryService.getInventory(itemId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory addInventory(@RequestBody @Valid Inventory inventory){
        return inventoryService.addInventory(inventory);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory updateInventory(@PathVariable("id") Long itemId, @RequestBody @Valid Inventory inventory){
        return inventoryService.updateInventory(itemId,inventory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@PathVariable("id") Long itemId){
        inventoryService.deleteItem(itemId);
    }
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItems(){
        inventoryService.deleteItems();
    }
}
