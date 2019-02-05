package com.hackerrank.sample.controller;

import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.model.Vendor;
import com.hackerrank.sample.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorConroller {
    @Autowired
    private VendorService vendorService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Vendor> getVendor(){
        return vendorService.getVendor();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vendor getVendor(@PathVariable("id") Long itemId){
        return vendorService.getVendor(itemId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Vendor addVendor(@RequestBody @Valid Vendor Vendor){
        return vendorService.addVendor(Vendor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vendor addVendor(@PathVariable("id") Long itemId, @RequestBody @Valid Vendor Vendor){
        return vendorService.updateVendor(itemId,Vendor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable("id") Long itemId){
        vendorService.deleteItem(itemId);
    }
    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(){
        vendorService.deleteItems();
    }
}